import React, { Component } from 'react';

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
    }

    columns = [{
        title: 'Start at',
        dataIndex: 'startTime',
        key: 'start'
    }, {
        title: 'First team',
        key: 'firstTeam',
        render: (value, row, index) => {
            return value.firstTeamDto === null ? '' : value.firstTeamDto.name;
        }
    }, {
        title: 'Second team',
        key: 'secondTeam',
        render: (value, row, index) => {
            return value.secondTeamDto === null ? '' : value.secondTeamDto.name;
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
            return value.played ? '' : <Button type="secondary" onClick={ref}>{label}</Button>
        }
    }, {
        title: '',
        key: 'delete',
        render: (value, row, index) => {
            return <Button type="danger" onClick={() => this.deleteHandler(value.id)}>Delete</Button>
        }
    }];

    deleteHandler = (id) => {
        (axios.delete('/games/' + id))
            .catch(error => {
                console.log(error);
                return;
            });
        this.reload(this.state.view);
    }

    cancelHandler = (id) => {
        (axios.put('/games/cancel/' + id))
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
    }

    retrieveHandler = (id) => {
        (axios.put('/games/retrieve/' + id))
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
        game.gameState = 'OK';
        const games = [...this.state.games];
        games[gameIndex] = game;
        this.setState({games: games});
    }

    playGamesHandler = () => {
        //let numPlayedGames;
        axios.put('/games/play')
//            .then(response => {
//                numPlayedGames = response.data;
//            })
            .catch(error => {
                console.log(error);
            });
//        console.log(numPlayedGames);
//        if (numPlayedGames > 0) {
            this.reload(this.state.view);
    }

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
    }

    changeViewHandler = (newView) => {
        this.setState({view: newView});
        this.reload(newView);
    }

    getOtherView = (view) => {
        switch(view) {
            case 'all':
                return 'scheduled';
            case 'scheduled':
                return 'all';
            default:
                return 'all';
        }
    }

    getSelect = () => {
        let options = (
            <Select defaultValue={"ALL"} style={{width: 200}} onChange={this.handleSelectChange}>
                <Option value="ALL">All</Option>
                {this.state.teams.map(team => {
                    return <Option value={team.id} key={team.id}>{team.name}</Option>
                })}
            </Select>
        );
        return options
    }

    render() {
        const { loading, games } = this.state;
        games.forEach((item, index) => {
            item.played = (item.firstTeamScore !== null);
        });

        let select = <div>Choose games by team  &nbsp; &nbsp;
            {this.getSelect()}
        </div>;

        return (
            <div>
                {this.state.view === 'all' ?
                <Row>{select}</Row> : null
                }

                <Button style={{marginTop: 1 + 'em'}}
                    type="secondary"
                    onClick={() => this.changeViewHandler(this.getOtherView(this.state.view))}
                >Show {this.getOtherView(this.state.view)} games</Button>

                {/*<Row><Link to={'/games/create'}><Button style={{marginTop: 1 + 'em'}}
                    type="primary"
                >Create game</Button></Link></Row> */}

                <Table dataSource={games} columns={this.columns} loading={loading} rowKey={'id'}/>
                <Button type="secondary" onClick={this.playGamesHandler}>Play games</Button>
            </div>
        );
    }
}

export default GamesContainer;