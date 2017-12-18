import React, { Component } from 'react';

import axios from '../../axios';
import {Table} from 'antd';

class HockeyPlayersContainer extends Component {

    state = {
        players: [],
        loading: true
    };

    componentDidMount() {
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

    columns = [{
        title: 'Name',
        dataIndex: 'name',
        key: 'name',
    }, {
        title: 'Post',
        dataIndex: 'post',
        key: 'post',
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
    }];


    render() {
        return (
            <div>
                <Table dataSource={this.state.players} columns={this.columns}/>
            </div>
        );
    }
}

export default HockeyPlayersContainer;