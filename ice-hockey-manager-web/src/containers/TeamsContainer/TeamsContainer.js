import React, {Component} from 'react';

import axios from '../../axios';
import {Table} from 'antd';
import {Link} from 'react-router-dom';

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

    columns = [
        {
            title: 'Name',
            dataIndex: 'name',

        }, {
            title: 'Competition country',
            render: (value, row, index) => {
                return <img
                    src={'http://localhost:3000/assets/pics/' + value.competitionCountry + '.png'}
                    alt={value.competitionCountry}
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

    render() {
        return (
            <Table dataSource={this.state.teams} columns={this.columns} rowKey={'id'}/>
        );
    }
}

export default TeamsContainer;