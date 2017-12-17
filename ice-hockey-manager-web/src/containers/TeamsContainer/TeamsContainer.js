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
                this.setState({
                    teams: response.data,
                    loading: false
                });
            })
            .catch(error => {
                console.log(error);
            });
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
        key: 'hockeyPlayersSize',
        render: (value, row, index) => {
            return value.hockeyPlayers.length;
        }
    }, {
        title: 'Action',
        render: (value, row, index) => {
            return <a href={'/teams/' + value.id}>view</a>;
        }

    }];

    render() {
        return (
            <Table dataSource={this.state.teams} columns={this.columns}/>
        );
    }
}

export default TeamsContainer;