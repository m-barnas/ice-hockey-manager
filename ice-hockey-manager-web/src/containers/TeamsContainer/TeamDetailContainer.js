import React, {Component} from 'react';
import { connect } from 'react-redux';
import axios from '../../axios';

// actions
import * as actions from '../../store/actions/index';

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
        };
        this.onChangeAddPlayer = this.onChangeAddPlayer.bind(this);
        this.onAddPlayer = this.onAddPlayer.bind(this);
        this.onRemovePlayer = this.onRemovePlayer.bind(this);
    }


    componentDidMount() {
        this.props.onSetAuthRedirectPath('/teams/' + this.props.match.params.id);
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
        axios.get('/players/get-free-agents')
            .then(responseHockeyPlayer => {
                if(responseHockeyPlayer.data.length > 0){
                    let player = responseHockeyPlayer.data[0];
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
                        selectedHockeyPlayerToAdd: {
                            key: 0,
                            label:<div/>
                        }
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
        if (this.state.selectedHockeyPlayerToAdd !== null && this.state.hockeyPlayers.length > 0) {
            axios({
                method: 'post',
                url: '/teams/spendMoneyFromBudget',
                headers: {
                    'Authorization': 'Bearer ' + this.props.token
                },
                data: {
                    teamId: this.props.match.params.id,
                    amount: this.state.hockeyPlayers.filter(function (player) {
                        return player.id === this.state.selectedHockeyPlayerToAdd.key;
                    }.bind(this))[0].price
                }
            }).then(spendMoneyResp => {
                if(spendMoneyResp.data.errorName){
                    alert(spendMoneyResp.data.errorName);
                    return;
                }
                axios({
                    method: 'post',
                    url: '/teams/addHockeyPlayer',
                    headers: {
                        'Authorization': 'Bearer ' + this.props.token
                    },
                    data: {
                        teamId: this.props.match.params.id,
                        hockeyPlayerId: this.state.selectedHockeyPlayerToAdd.key
                    }
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
        axios({
            method: 'post',
            url: '/teams/removeHockeyPlayer',
            headers: {
                'Authorization': 'Bearer ' + this.props.token
            },
            data: {
                teamId: this.props.match.params.id,
                hockeyPlayerId: playerId
            }
        }).then(responseAddPlayer => {
            this.getFreeAgents();
            this.setState({
                team: responseAddPlayer.data
            });
        });

    }

    getActions(playerId) {
        return this.hasPrivileges() ? [<a onClick={() => this.onRemovePlayer(playerId)}>remove</a>] : [];
    };

    hasPrivileges() {
        return this.props.isAuthenticated && this.state.isHumanPlayer && this.state.humanPlayer.id === this.props.userId;
    }

    render() {
        let humanPlayer = this.state.isHumanPlayer
            ? <span>{this.state.humanPlayer.username}</span>
            : 'Not added yet';

        let addPlayerRow = this.hasPrivileges() ? (
            <Row>
                <Button type="primary" onClick={this.onAddPlayer}>Add player</Button>&nbsp;&nbsp;

                <Select style={{width: 200}} onChange={this.onChangeAddPlayer} value={this.state.selectedHockeyPlayerToAdd} labelInValue={true}>
                    {this.state.hockeyPlayers.map(function (player, index) {
                        return <Option value={player.id} key={index}>{player.name} ({player.price})</Option>;
                    })}
                </Select>
            </Row>
        ) : (
            <Row/>
        );
        return (
            <div>
                <Row><h2>{this.state.team.name}</h2></Row>
                <Row><h3>Competition
                    country: {transformCountryLabel(this.state.team.competitionCountry)}</h3>
                </Row>
                <Row><h3>Budget: {this.state.team.budget}</h3></Row>
                <Row><h3>Manager: {humanPlayer}</h3></Row>
                {addPlayerRow}
                <List
                    header={<h2>Hockey players of team</h2>}
                    dataSource={this.state.team.hockeyPlayers}
                    renderItem={player => (
                        <List.Item actions={this.getActions(player.id)}>
                            {player.name}
                        </List.Item>)}
                />
            </div>
        );
    }
}

const mapStateToProps = state => {
    return {
        hasRoleAdmin: state.auth.role === 'ADMIN',
        isAuthenticated: state.auth.token !== null,
        userId: state.auth.userId,
        token: state.auth.token
    };
};

const mapDispatchToProps = dispatch => {
    return {
        onSetAuthRedirectPath: (path) => dispatch(actions.setAuthRedirectPath(path))
    };
};

export default connect( mapStateToProps, mapDispatchToProps )(TeamDetailContainer);