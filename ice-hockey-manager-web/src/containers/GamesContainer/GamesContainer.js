import React, { Component } from 'react';
import { connect } from 'react-redux';

// actions
import * as actions from '../../store/actions/index';

import axios from '../../axios';
import {Table, Select, Row, Button} from 'antd';
import {Link} from 'react-router-dom';

const Option = Select.Option;

class GamesContainer extends Component {


    state = {
        games: [],
        teams: [],
        view: 'all',
        loading: true
    };

    componentDidMount() {
        this.props.onSetAuthRedirectPath('/games');
        this.reload(this.state.view);
        axios.get('/teams/all')
            .then(response => {
                this.setState({
                    teams: response.data,
                });
            })
            .catch(error => {
                console.log(error);
            });
    }

    reload = (view) => {
        this.setState({
            loading: true
        });
        axios.get('/games/' + view)
            .then(response => {
                this.setState({
                    games: response.data,
                    loading: false
                });
            })
            .catch(error => {
                console.log(error);
            });
    };

    columns = [{
        title: 'Start at',
        key: 'start',
        render: (value, row, index) => {
            return value.startTime.replace("T", " ");
        }
    }, {
        title: 'First team',
        key: 'firstTeam',
        render: (value, row, index) => {
            return value.firstTeamDto === null ? '' :
                    <Link to={'/teams/' + value.firstTeamDto.id}>{value.firstTeamDto.name}</Link>;
        }
    }, {
        title: 'Second team',
        key: 'secondTeam',
        render: (value, row, index) => {
            return value.secondTeamDto === null ? '' :
                    <Link to={'/teams/' + value.secondTeamDto.id}>{value.secondTeamDto.name}</Link>;
        }
    }, {
        title: 'State',
        key: 'state',
        render: (value, row, index) => {
            return value.gameState;
        }
    }, {
        title: 'Score',
        key: 'score',
        render: (value, row, index) => {
            return value.played ? value.firstTeamScore + ' : ' + value.secondTeamScore : '-';
        }
    }, {
        title: '',
        key: 'cancelretrieve',
        render: (value, row, index) => {
            let ref;
            let label;
            switch (value.gameState) {
                case 'OK':
                    ref = () => this.cancelHandler(value.id);
                    label = 'Cancel';
                    break;
                case 'CANCELED':
                    ref = () => this.retrieveHandler(value.id);
                    label = 'Retrieve';
                    break;
                default:
                    break;
            }
            return value.played || !this.props.hasRoleAdmin ? '' :
                    <Button type="secondary" onClick={ref}>{label}</Button>
        }
    }, {
        title: '',
        key: 'changeStartTime',
        render: (value, row, index) => {
            return value.played || !this.props.hasRoleAdmin ? '' :
                    <Link to={'/games/edit/' + value.id}>
                        <Button type="secondary">Edit</Button>
                    </Link>
        }
    }, {
        title: '',
        key: 'delete',
        render: (value, row, index) => {
            return this.props.hasRoleAdmin ? <Button type="danger" onClick={() => this.deleteHandler(value.id)}>Delete</Button> : '';
        }
    }];

    deleteHandler = (id) => {
        axios({
            method: 'delete',
            url: '/games/' + id,
            headers: {
                'Authorization': 'Bearer ' + this.props.token
            }
        }).then(() => {
                this.reload(this.state.view);
            })
            .catch(error => {
                console.log(error);
                return;
            });
    };

    cancelHandler = (id) => {
        axios({
            method: 'put',
            url: '/games/cancel/' + id,
            headers: {
                'Authorization': 'Bearer ' + this.props.token
            }
        }).then(() => {
                if (this.state.view === 'scheduled') {
                    this.reload(this.state.view);
                }
            })
            .catch(error => {
                console.log(error);
                return;
            });
        const gameIndex = this.state.games.findIndex(g => {
            return g.id === id;
        });
        const game = {
            ...this.state.games[gameIndex]
        };
        game.gameState = 'CANCELED';
        const games = [...this.state.games];
        games[gameIndex] = game;
        this.setState({games: games});
    };

    retrieveHandler = (id) => {
        axios({
            method: 'put',
            url: '/games/retrieve/' + id,
            headers: {
                'Authorization': 'Bearer ' + this.props.token
            }
        }).catch(error => {
                console.log(error);
                return;
            });
        const gameIndex = this.state.games.findIndex(g => {
            return g.id === id;
        });
        const game = {
            ...this.state.games[gameIndex]
        };
        game.gameState = 'OK';
        const games = [...this.state.games];
        games[gameIndex] = game;
        this.setState({games: games});
    };

    playGamesHandler = () => {
        axios({
            method: 'put',
            url: '/games/play',
            headers: {
                'Authorization': 'Bearer ' + this.props.token
            }
        }).then((response, data) => {
                if (response.data > 0) {
                    this.reload(this.state.view);
                }
            })
            .catch(error => {
                console.log(error);
            });
    };

    handleSelectChange = (selected) => {
        this.setState({
            loading: true
        });
        if (selected === "ALL") {
            this.reload('all');
        } else {
            axios.get('/games/byteam/?teamId=' + selected)
                .then(response => {
                    this.setState({
                        games: response.data,
                        loading: false
                    });
                })
                .catch(error => {
                    console.log(error);
                });
        }
    };

    changeViewHandler = (newView) => {
        this.setState({view: newView});
        this.reload(newView);
    };

    getOtherView = (view) => {
        switch(view) {
            case 'all':
                return 'scheduled';
            case 'scheduled':
                return 'all';
            default:
                return 'all';
        }
    };

    getSelect = () => {
        let options = (
            <Select defaultValue={"ALL"} style={{width: 200}} onChange={this.handleSelectChange}>
                <Option value="ALL">All</Option>
                {this.state.teams.map(team => {
                    return <Option value={team.id} key={team.id}>{team.name}</Option>
                })}
            </Select>
        );
        return options;
    };

    render() {
        const { loading, games } = this.state;
        games.forEach((item, index) => {
            item.played = (item.firstTeamScore !== null);
        });

        let select = <div>Choose games by team  &nbsp; &nbsp;
            {this.getSelect()}
        </div>;

        let createGameBtn = this.props.hasRoleAdmin ? (
            <Row>
                <Link to={'/games/create'}>
                    <Button style={{marginBottom: 1 + 'em'}} type="primary">
                        Create game
                    </Button>
                </Link>
            </Row>
        ) : (
            <Row/>
        );

        let playGamesBtn = this.props.hasRoleAdmin ? (
            <Row>
                <Button type="secondary" onClick={this.playGamesHandler}>
                    Play games*
                </Button>
                <p style={{marginTop: 1 + 'em', fontSize: 12}} >* Get results for all games with state OK and start time in the past.</p>
            </Row>
        ) : (
            <Row/>
        );

        return (
            <div>
                {this.state.view === 'all' ?
                <Row>{select}</Row> : null
                }

                <Button style={{marginTop: 1 + 'em', marginBottom: 1 + 'em'}}
                    type="secondary"
                    onClick={() => this.changeViewHandler(this.getOtherView(this.state.view))}
                >Show {this.getOtherView(this.state.view)} games</Button>

                {createGameBtn}

                <Table dataSource={games} columns={this.columns} loading={loading} rowKey={'id'}/>

                {playGamesBtn}
            </div>
        );
    }
}

const mapStateToProps = state => {
    return {
        hasRoleAdmin: state.auth.role === 'ADMIN',
        token: state.auth.token
    };
};

const mapDispatchToProps = dispatch => {
    return {
        onSetAuthRedirectPath: (path) => dispatch(actions.setAuthRedirectPath(path))
    };
};

export default connect( mapStateToProps, mapDispatchToProps )(GamesContainer);