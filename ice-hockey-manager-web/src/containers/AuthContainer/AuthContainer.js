import React, { Component } from 'react';
import { Redirect } from 'react-router-dom';
import { connect } from 'react-redux';

// css
import './AuthContainer.css';

// actions
import * as actions from '../../store/actions/index';

// antd
import { Form, Icon, Input, Button, Col, Row, Alert } from 'antd';

const FormItem = Form.Item;

class AuthContainer extends Component {

    handleSubmit = (e) => {
        e.preventDefault();
        this.props.form.validateFields((err, values) => {
            if (!err) {
                this.props.onAuth(values.email, values.password);
            }
        });
    };

    render() {
        let errorMessage = null;
        if (this.props.error) {
            errorMessage = (
                <Alert
                    message="Invalid credentials!"
                    description="Please, provide valid email and password."
                    type="error"
                    closable
                />);
        }

        let authRedirect = null;
        if (this.props.isAuthenticated) {
            authRedirect = <Redirect to={this.props.authRedirectPath}/>
        }

        const { getFieldDecorator } = this.props.form;
        return (
            <Row>
                {authRedirect}
                {errorMessage}
                <Col span={8} offset={8}>
                    <Form onSubmit={this.handleSubmit} className="login-form">
                        <FormItem>
                            {getFieldDecorator('email', {
                                rules: [{ required: true, message: 'Please input your email!' }],
                            })(
                                <Input prefix={<Icon type="mail" style={{ color: 'rgba(0,0,0,.25)' }} />} placeholder="Email" />
                            )}
                        </FormItem>
                        <FormItem>
                            {getFieldDecorator('password', {
                                rules: [{ required: true, message: 'Please input your Password!' }],
                            })(
                                <Input prefix={<Icon type="lock" style={{ color: 'rgba(0,0,0,.25)' }} />} type="password" placeholder="Password" />
                            )}
                        </FormItem>
                        <FormItem>
                            <Button type="primary" htmlType="submit" className="login-form-button">
                                Log in
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
        error: state.auth.error,
        isAuthenticated: state.auth.token !== null,
        authRedirectPath: state.auth.authRedirectPath
    };
};

const mapDispatchToProps = dispatch => {
    return {
        onAuth: (email, password) => dispatch(actions.auth(email, password))
    };
};

export default connect(mapStateToProps, mapDispatchToProps)(Form.create()(AuthContainer));