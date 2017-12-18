import React, {Component} from 'react';

import axios from '../../axios';
import {Table, Select, Row, Button} from 'antd';
import {Link} from 'react-router-dom';

import {transformCountryLabel} from '../../other/Helper';

const Option = Select.Option;

class TeamsContainer extends Component {
    constructor(props) {
        super(props);
        this.state = {
            teams: [],
            loading: true
        };
        this.handleSelectChange = this.handleSelectChange.bind(this);
        this.getAllTeams = this.getAllTeams.bind(this);
    }

    columns = [
        {
            title: 'Name',
            dataIndex: 'name',

        }, {
            title: 'Competition country',
            render: (value, row, index) => {
                return <img
                    src={'http://localhost:3000/assets/pics/' + value.competitionCountry + '.png'}
                    alt={transformCountryLabel(value.competitionCountry)}
                />;
            },
        }, {
            title: 'Number of players',
            render: (value, row, index) => {
                return value.hockeyPlayers.length;
            }
        }, {
            title: 'Action',
            render: (value, row, index) => {
                return <Link to={'/teams/' + value.id}>view</Link>;
            }

        }];

    componentDidMount() {
        this.getAllTeams();
    }

    getAllTeams() {
        axios.get('/teams/all')
            .then(response => {
                this.setState({
                    teams: response.data,
                    loading: false
                });
            })
            .catch(error => {
                console.log(error);
            });
    }

    handleSelectChange(selected) {
        this.setState({
            loading: true
        });
        if (selected === "ALL") {
            this.getAllTeams();
        } else {
            axios.get('/teams/getByCompetitionCountry/' + selected)
                .then(response => {
                    this.setState({
                        teams: response.data,
                        loading: false
                    });
                })
                .catch(error => {
                    console.log(error);
                });
        }
    }

    render() {
        let select = <div>Choose teams by country  &nbsp; &nbsp;
            <Select defaultValue={"ALL"} style={{width: 200}} onChange={this.handleSelectChange}>
                <Option value="ALL">All</Option>
                <Option value="CZECH_REPUBLIC">{transformCountryLabel("CZECH_REPUBLIC")}</Option>
                <Option value="ENGLAND">{transformCountryLabel("ENGLAND")}</Option>
                <Option value="SLOVAKIA">{transformCountryLabel("SLOVAKIA")}</Option>
                <Option value="GERMANY">{transformCountryLabel("GERMANY")}</Option>
                <Option value="FINLAND">{transformCountryLabel("FINLAND")}</Option>
                <Option value="FRANCE">{transformCountryLabel("FRANCE")}</Option>
            </Select>
        </div>;

        return (
            <div>
                <Row>{select}</Row>
                <Row><Link to={'/teams/create'}><Button type="primary">Create team</Button></Link></Row>
                <Table dataSource={this.state.teams} columns={this.columns} rowKey={'id'}/>
            </div>
        );
    }
}

export default TeamsContainer;