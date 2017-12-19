import React, { Component } from 'react';

import axios from '../../axios';
import {Table, Select, Button, Row} from 'antd';
import {transformPositionLabel} from "../../other/Helper";
import {Link} from "react-router-dom";

const Option = Select.Option;

class HockeyPlayersContainer extends Component {

    constructor(props) {
        super(props);
        this.state = {
            players: [],
            teams: [],
            teamSelected: "ALL",
            postSelected: "ALL",
            loading: true
        };
        this.handleTeamSelectChange = this.handleTeamSelectChange.bind(this);
        this.handlePositionSelectChange = this.handlePositionSelectChange.bind(this);
        this.getAllPlayers = this.getAllPlayers.bind(this);
        this.getAllTeams = this.getAllTeams.bind(this);
        this.getFreeAgents = this.getFreeAgents.bind(this);
    }

    columns = [{
        title: 'Name',
        dataIndex: 'name',
        key: 'name',
    }, {
        title: 'Post',
        key: 'post',
        render: (value, row, index) => {
            return transformPositionLabel(value.post);
        },
        sorter: (a, b) => a.post.length - b.post.length
    }, {
        title: 'Attack Skill',
        dataIndex: 'attackSkill',
        key: 'attSkill',
        sorter: (a, b) => a.attackSkill - b.attackSkill
    }, {
        title: 'Defense Skill',
        dataIndex: 'defenseSkill',
        key: 'defSkill',
        sorter: (a, b) => a.defenseSkill - b.defenseSkill
    }, {
        title: 'Price',
        dataIndex: 'price',
        key: 'price',
        sorter: (a, b) => a.price - b.price
    }, {
        key: 'delete',
        render: (value, row, index) => {
            return <Button type="primary" onClick={() => this.onRemovePlayer(value.id)}>Delete</Button>
        }
    }];

    componentDidMount() {
        this.getAllPlayers();
        this.getAllTeams();
    }

    getAllTeams() {
        axios.get('/teams/all')
            .then(response => {
                console.log(response);
                this.setState({
                    teams: response.data,
                    loading: false
                });
            })
            .catch(error => {
                console.log(error);
            });
    }

    getAllPlayers() {
        axios.get('/players/all')
            .then(response => {
                console.log(response);
                this.setState({
                    players: response.data,
                    loading: false
                });
            })
            .catch(error => {
                console.log(error);
            });
    }

    getFreeAgents() {
        axios.get('/players/getFreeAgents')
            .then(response => {
                this.setState({
                    players: response.data,
                    loading: false
                });
            })
            .catch(error => {
                console.log(error);
            });
    }

    handleTeamSelectChange(selected) {
        this.setState({
            teamSelected: selected,
            postSelected: "ALL",
            loading: true
        });
        if (selected === "ALL") {
            this.getAllPlayers();
        } else if (selected === "FREE") {
            this.getFreeAgents();
        } else {
            axios.get('/players/getByTeam/' + selected)
                .then(response =>  {
                    this.setState({
                        players: response.data,
                        loading: false
                    });
                })
                .catch(error => {
                    console.log(error);
                });
        }
    }

    handlePositionSelectChange(selected) {
        this.setState({
            teamSelected: "ALL",
            postSelected: selected,
            loading: true
        });
        if (selected === "ALL") {
            this.getAllPlayers();
        } else {
            axios.get('/players/getByPost/' + selected)
                .then(response =>  {
                    this.setState({
                        players: response.data,
                        loading: false
                    });
                })
                .catch(error => {
                    console.log(error);
                });
        }
    }

    onRemovePlayer(playerId) {
        axios.delete('/players/' + playerId)
            .then(this.getAllPlayers);
    }

    render() {
        const teamsArray = [];
        for (let i in this.state.teams) {
            teamsArray.push(<Option key={i} value={this.state.teams[i].id}>{this.state.teams[i].name}</Option>);
        }

        let teamSelect = (
            <div>Filter by teams &nbsp;
                <Select value={this.state.teamSelected} defaultValue={"ALL"} style={{width: 200}} onChange={this.handleTeamSelectChange}>
                    <Option value={"ALL"}>All players</Option>
                    <Option value={"FREE"}>Free agents</Option>
                    {teamsArray}
                </Select>
            </div>
        );

        let postSelect = (
            <div>Filter by position &nbsp;
                <Select value={this.state.postSelected} defaultValue={"ALL"} style={{width: 200}} onChange={this.handlePositionSelectChange}>
                    <Option value={"ALL"}>All posts</Option>
                    <Option value={"GOALKEEPER"}>Goalkeepers</Option>
                    <Option value={"DEFENSEMAN"}>Defensemen</Option>
                    <Option value={"LEFT_WING"}>Left wings</Option>
                    <Option value={"CENTER"}>Centers</Option>
                    <Option value={"RIGHT_WING"}>Right wings</Option>
                </Select>
            </div>
        );
        return (
            <div>
                <div>{teamSelect}</div>
                <div>{postSelect}</div>
                <Row><Link to={'/players/create'}><Button type="primary">Create player</Button></Link></Row>
                <Table dataSource={this.state.players} columns={this.columns} rowKey={'id'}/>
            </div>
        );
    }
}

export default HockeyPlayersContainer;