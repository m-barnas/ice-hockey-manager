import React, {Component} from 'react';

import axios from '../../axios';
import {Row, List, Select, Button} from 'antd';
import {transformCountryLabel} from '../../other/Helper';

const Option = Select.Option;

class TeamDetailContainer extends Component {

    constructor(props) {
        super(props);
        this.state = {
            team: {
                hockeyPlayers:  this.getFreeAgents()
            },
            humanPlayer: {},
            loading: true,
            isHumanPlayer: false,
            selectedHockeyPlayerToAdd: {
                label: '',
                key: ''
            },

            hockeyPlayers: [],
            firstHockeyPlayerId: null
        };
        this.onChangeAddPlayer = this.onChangeAddPlayer.bind(this);
        this.onAddPlayer = this.onAddPlayer.bind(this);
        this.onRemovePlayer = this.onRemovePlayer.bind(this);
    }


    componentDidMount() {
        axios.get('/teams/' + this.props.match.params.id)
            .then(responseTeam => {
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

            })
            .catch(error => {
                console.log(error);
            });
    }

    getFreeAgents() {
        axios.get('/players/getFreeAgents')
            .then(responseHockeyPlayer => {
                let player = responseHockeyPlayer.data[0];
                if(player !== undefined){
                    this.setState({
                        hockeyPlayers: responseHockeyPlayer.data,
                        selectedHockeyPlayerToAdd: {
                            key: player.id,
                            label:<div>{player.name} ({player.price})</div>
                        }
                    });
                } else {
                    this.setState({
                        hockeyPlayers: responseHockeyPlayer.data,
                    });
                }
            });
    }

    onChangeAddPlayer(playerLabel) {
        let player = this.state.hockeyPlayers.filter(function (player) {
            return player.id === playerLabel.key;
        })[0];

        this.setState({
            selectedHockeyPlayerToAdd: {
                key:player.id,
                label:<div>{player.name} ({player.price})</div>
            }
        });
    }

    onAddPlayer() {
        if (this.state.selectedHockeyPlayerToAdd !== null) {
            axios.post('/teams/spendMoneyFromBudget', {
                teamId: this.props.match.params.id,
                amount: this.state.hockeyPlayers.filter(function (player) {
                    return player.id === this.state.selectedHockeyPlayerToAdd.key;
                }.bind(this))[0].price
            }).then(spendMoneyResp => {
                if(spendMoneyResp.data.errorName){
                    alert(spendMoneyResp.data.errorName);
                    return;
                }
                axios.post('/teams/addHockeyPlayer', {
                    teamId: this.props.match.params.id,
                    hockeyPlayerId: this.state.selectedHockeyPlayerToAdd.key
                }).then(responseAddPlayer => {
                    this.setState({
                        team: responseAddPlayer.data
                    });
                    this.getFreeAgents();

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
        this.getFreeAgents();
    }

    render() {
        let humanPlayer = this.state.isHumanPlayer
            ? <span>{this.state.humanPlayer.username}</span>
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

                    <Select style={{width: 200}} onChange={this.onChangeAddPlayer} value={this.state.selectedHockeyPlayerToAdd} labelInValue={true}>
                        {this.state.hockeyPlayers.map(function (player, index) {
                            return <Option value={player.id} key={index}>{player.name} ({player.price})</Option>;
                        })}
                    </Select>
                </Row>
                <List
                    header={<h2>Hockey players of team</h2>}
                    dataSource={this.state.team.hockeyPlayers}
                    renderItem={player => (
                        <List.Item actions={[<a onClick={() => this.onRemovePlayer(player.id)}>remove</a>]}>
                            {player.name}
                        </List.Item>)}
                />
            </div>
        );
    }

}

export default TeamDetailContainer;