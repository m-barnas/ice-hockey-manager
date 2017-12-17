import React, {Component} from 'react';

import axios from '../../axios';
import {Table} from 'antd';

class TeamsContainer extends Component {

    state = {
        teams: [],
        loading: true
    };

    componentDidMount() {
        axios.get('/teams/all')
            .then(response => {
                this.setState(this.processResponse(response.data));
            })
            .catch(error => {
                console.log(error);
            });
    }

    processResponse(data) {
        let teamsIterable = data;
        for (let team of teamsIterable) {
            team.hockeyPlayersSize = team.hockeyPlayers.length;
        }
        return {
            teams: teamsIterable,
            loading: false
        };
    }

    columns = [{
        title: 'Name',
        dataIndex: 'name',
        key: 'name',
    }, {
        title: 'Competition country',
        key: 'competitionCountry',
        render: (value, row, index) => {
            return <img src={'http://localhost:3000/assets/pics/' + value.competitionCountry + '.png'}/>;
        },
    }, {
        title: 'Number of players',
        dataIndex: 'hockeyPlayersSize',
        key: 'hockeyPlayersSize',
    }];

    render() {
        return (
            <Table dataSource={this.state.teams} columns={this.columns}/>
        );
    }
}

export default TeamsContainer;