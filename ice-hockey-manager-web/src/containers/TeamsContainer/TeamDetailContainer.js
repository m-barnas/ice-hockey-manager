import React, {Component} from 'react';

import axios from '../../axios';
import {Row, List, Select, Button} from 'antd';
import {Link} from 'react-router-dom';
import transformCountryLabel from '../../other/Helper';

const Option = Select.Option;

class TeamDetailContainer extends Component {

    constructor(props) {
        super(props);
        this.state = {
            team: {
                hockeyPlayers: []
            },
            humanPlayer: {},
            loading: true,
            isHumanPlayer: false,
            selectedHockeyPlayerToAdd: null,
            hockeyPlayers: []
        };
        this.onChangeAddPlayer = this.onChangeAddPlayer.bind(this);
        this.onAddPlayer = this.onAddPlayer.bind(this);
        this.onRemovePlayer = this.onRemovePlayer.bind(this);
    }


    componentDidMount() {
        axios.get('/teams/' + this.props.match.params.id)
            .then(responseTeam => {
                console.log(responseTeam.data);
                if (responseTeam.data.humanPlayerId != null) {
                    axios.get('/managers/' + responseTeam.data.humanPlayerId)
                        .then(responseHumanPlayer => {
                            this.setState({
                                team: responseTeam.data,
                                humanPlayer: responseHumanPlayer.data,
                                loading: false,
                                isHumanPlayer: true
                            });
                        });
                } else {
                    this.setState({
                        team: responseTeam.data,
                        loading: false
                    });
                }
                // axios.get('/players/all')
                //     .then(responseHockeyPlayer => {
                this.setState({
                    // TODO prepsat na responseHockeyPlayer.data
                    hockeyPlayers: [
                        {
                            id: 1,
                            name: 'P1',
                            price: 100
                        },
                        {
                            id: 2,
                            name: 'P2',
                            price: 120
                        },
                        {
                            id: 3,
                            name: 'P3',
                            price: 130
                        }
                    ]

                })
                // });
            })
            .catch(error => {
                console.log(error);
            });
    }


    onChangeAddPlayer(playerId) {
        this.setState({
            selectedHockeyPlayerToAdd: playerId
        });
    }

    onAddPlayer() {
        if (this.state.selectedHockeyPlayerToAdd !== null) {
            axios.post('/teams/addHockeyPlayer', {
                teamId: this.props.match.params.id,
                hockeyPlayerId: this.state.selectedHockeyPlayerToAdd
            }).then(responseAddPlayer => {
                axios.post('/teams/spendMoneyFromBudget', {
                    teamId: this.props.match.params.id,
                    amount: this.state.hockeyPlayers.filter(function (player) {
                        return player.id === this.state.selectedHockeyPlayerToAdd;
                    }.bind(this))[0].price
                }).then(responseAddPlayer => {

                    this.setState({
                        team: responseAddPlayer.data
                    });
                });
            });
        }
    }

    onRemovePlayer(playerId) {
        axios.post('/teams/removeHockeyPlayer', {
            teamId: this.props.match.params.id,
            hockeyPlayerId: playerId
        }).then(responseAddPlayer => {
            this.setState({
                team: responseAddPlayer.data
            });
        });
    }

    render() {
        let humanPlayer = this.state.isHumanPlayer
            ? <Link to={'/managers/' + this.state.humanPlayer.id}>{this.state.humanPlayer.username}</Link>
            : 'Not added yet';

        return (
            <div>
                <Row><h2>{this.state.team.name}</h2></Row>
                <Row><h3>Competition
                    country: {transformCountryLabel(this.state.team.competitionCountry)}</h3>
                </Row>
                <Row><h3>Budget: {this.state.team.budget}</h3></Row>
                <Row><h3>Manager: {humanPlayer}</h3></Row>
                <Row>
                    <Button type="primary" onClick={this.onAddPlayer}>Add player</Button>&nbsp;&nbsp;
                    <Select style={{width: 200}} onChange={this.onChangeAddPlayer}>
                        {this.state.hockeyPlayers.map(function (player, index) {
                            return <Option value={player.id} key={index}>{player.name}</Option>;
                        })}
                    </Select>
                </Row>
                <List
                    header={<h2>Hockey players of team</h2>}
                    dataSource={this.state.team.hockeyPlayers}
                    renderItem={player => (
                        <List.Item actions={[<a onClick={() => this.onRemovePlayer(player.id)}>remove</a>]}>
                            <Link to={'/players/' + player.id}>{player.name}</Link>
                        </List.Item>)}
                />
            </div>
        );
    }

}

export default TeamDetailContainer;