import React, { Component } from 'react';
import { connect } from 'react-redux';
import {Redirect} from 'react-router-dom';

// actions
import * as actions from '../../store/actions/index';

// axios
import axios from '../../axios';

// antd
import { Form, Icon, Input, Button, Col, Row, Alert } from 'antd';

const FormItem = Form.Item;

class ManagerEditContainer extends Component {

        state = {
            error: null,
            redirect: false
        };


    componentDidMount() {
        this.props.onSetAuthRedirectPath('/managers/changepassword');
    }

    handleSubmit = (e) => {
        e.preventDefault();
        this.props.form.validateFields((err, values) => {
            if (!err) {
                axios({
                    method: 'post',
                    url: '/managers/changepassword',
                    headers: {
                        'Authorization': 'Bearer ' + this.props.token
                    },
                    params: {
                        id: this.props.userId,
                        oldPass: values.oldPass,
                        newPass: values.newPass
                    }
                }).then(res => {
                    console.log(res);
                    this.setState({
                        error: null,
                        redirect: true
                    });

                }).catch(err => {
                    console.log(err);
                    this.setState({error: err});
                });
            }
        });
    };

    render() {
        if (!this.props.isAuthenticated) {
            return <Redirect to="/auth" />;
        }
        if (this.state.redirect) {
            return <Redirect to="/managers" />
        }
        let errorMessage = null;
        if (this.state.error) {
            errorMessage = (
                <Alert
                    message="Wrong old password!"
                    description="Please, enter your valid old password."
                    type="error"
                    closable
                />);
        }
        const { getFieldDecorator } = this.props.form;
        return (
            <Row>
                <h2>Change password</h2>
                {errorMessage}
                <Col span={8} offset={8}>
                    <Form onSubmit={this.handleSubmit} className="login-form">
                        <FormItem>
                            {getFieldDecorator('oldPass', {
                                rules: [{ required: true, message: 'Please input your old Password!' }],
                            })(
                                <Input prefix={<Icon type="lock" style={{ color: 'rgba(0,0,0,.25)' }} />} type="password" placeholder="Old Password" />
                            )}
                        </FormItem>
                        <FormItem>
                            {getFieldDecorator('newPass', {
                                rules: [{ required: true, message: 'Please input your new Password!' }],
                            })(
                                <Input prefix={<Icon type="lock" style={{ color: 'rgba(0,0,0,.25)' }} />} type="password" placeholder="New Password" />
                            )}
                        </FormItem>
                        <FormItem>
                            <Button type="primary" htmlType="submit" className="login-form-button">
                                Confirm change
                            </Button>
                        </FormItem>
                    </Form>
                </Col>
            </Row>
        );
    }
}

const mapStateToProps = state =>  {
    return {
        isAuthenticated: state.auth.token !== null,
        token: state.auth.token,
        userId: state.auth.userId,

    };
};

const mapDispatchToProps = dispatch => {
    return {
        onSetAuthRedirectPath: (path) => dispatch(actions.setAuthRedirectPath(path))
    };
};

export default connect( mapStateToProps, mapDispatchToProps )(Form.create()(ManagerEditContainer));