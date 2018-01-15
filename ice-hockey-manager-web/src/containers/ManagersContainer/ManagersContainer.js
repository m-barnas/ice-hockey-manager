import React, { Component } from 'react';
import {Link} from "react-router-dom";
import { connect } from 'react-redux';

// actions
import * as actions from '../../store/actions/index';

// css
import "./ManagersContainer.css";

// antd
import { List, Avatar, Button } from 'antd';

class ManagersContainer extends Component {

    componentDidMount () {
        this.props.onSetAuthRedirectPath('/managers');
        this.props.onFetchManagers();
    }

    getActions(managerId) {
        if (this.props.userId === managerId) {
            return [<Link to="/managers/changepassword">Change Password</Link>];
        }
        if (this.props.userId === null || this.props.role === 'USER') {
            return [];
        }
        return [<Button onClick={() => this.props.onRemoveManager(managerId, this.props.token)} type="danger">Delete</Button>];
    };

    render() {
        let managers = this.props.managers;
        return (
            <div>
                <h2>Managers</h2>
            <List
                itemLayout="horizontal"
                loading={this.props.loading}
                dataSource={managers}
                renderItem={manager => (
                    <List.Item actions={this.getActions(manager.id)}>
                        <List.Item.Meta
                            avatar={<Avatar className="Avatar" src="https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png" />}
                            title={manager.username}
                            description={manager.email}
                        />
                        <div className="role">{manager.role}</div>
                    </List.Item>
                )}
            />
            </div>
        );
    }
}

const mapStateToProps = state => {
    return {
        managers: state.manager.managers,
        loading: state.manager.loading,
        token: state.auth.token,
        userId: state.auth.userId,
        role: state.auth.role
    };
};

const mapDispatchToProps = dispatch => {
    return {
        onFetchManagers: () => dispatch( actions.fetchManagers() ),
        onRemoveManager: (managerId, token) => dispatch( actions.removeManager(managerId, token) ),
        onSetAuthRedirectPath: (path) => dispatch(actions.setAuthRedirectPath(path))
    };
};

export default connect(mapStateToProps, mapDispatchToProps)(ManagersContainer);