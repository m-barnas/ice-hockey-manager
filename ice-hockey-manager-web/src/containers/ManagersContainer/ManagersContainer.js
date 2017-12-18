import React, { Component } from 'react';

// css
import "./ManagersContainer.css";

// axios
import axios from '../../axios';

// antd
import { List, Avatar } from 'antd';

class ManagersContainer extends Component {

    state = {
        managers: [],
        selectedManagerId: null,
        loading: true
    };

    componentDidMount () {
        axios.get('/managers/all')
            .then( response => {
                this.setState({
                    loading: false,
                    managers: response.data
                });
            })
            .catch(error => {
                console.log(error);
            });
    }

    render() {
        const { loading, managers } = this.state;
        return (
            <List
                itemLayout="horizontal"
                loading={loading}
                dataSource={managers}
                renderItem={manager => (
                    <List.Item >
                        <List.Item.Meta
                            avatar={<Avatar className="Avatar" src="https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png" />}
                            title={manager.username}
                            description="Dummy description"
                        />
                    </List.Item>
                )}
            />
        );
    }
}

export default ManagersContainer;