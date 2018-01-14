import React, {Component} from 'react';
import { connect } from 'react-redux';
import axios from '../../axios';
import {Link, Redirect} from 'react-router-dom';

// actions
import * as actions from '../../store/actions/index';

import {Form, Select, Button, DatePicker} from 'antd';

const FormItem = Form.Item;
const Option = Select.Option;


class GameCreateContainer extends Component {

    constructor(props) {
        super(props);
        this.state = {
            confirmDirty: false,
            teams: [],
            redirect: false
        };
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    componentDidMount() {
        this.props.onSetAuthRedirectPath('/games/create');
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

    handleSubmit(e) {
        e.preventDefault();
        this.props.form.validateFieldsAndScroll((err, values) => {
            if (!err) {
            console.log("values: ", values);
                axios({
                    method: 'post',
                    url: '/games/create',
                    headers: {
                        'Authorization': 'Bearer ' + this.props.token
                    },
                    data: values
                }).then(response => {
                        this.setState({
                            redirect: true
                        });
                    })
                    .catch(error => {
                        console.log(error);
                    });
            }
        });
    }

    handleConfirmBlur(e) {
        const value = e.target.value;
        this.setState({confirmDirty: this.state.confirmDirty || !!value});
    }

    checkVariousTeams = (rule, value, callback) => {
        const form = this.props.form;
        if (value && form.getFieldValue('secondTeamId') === form.getFieldValue('firstTeamId')) {
          callback('First and second team must be different!');
        } else {
          callback();
        }
    };

    render() {
        if (!this.props.hasRoleAdmin) {
            return <Redirect to="/auth" />
        }
        if (this.state.redirect){
           return <Redirect to="/games"/>;
        }
        const {getFieldDecorator} = this.props.form;

        const formItemLayout = {
            labelCol: {
                xs: {span: 24},
                sm: {span: 4},
            },
            wrapperCol: {
                xs: {span: 24},
                sm: {span: 4},
            },
        };
        const tailFormItemLayout = {
            wrapperCol: {
                xs: {
                    span: 24,
                    offset: 0,
                },
                sm: {
                    span: 16,
                    offset: 4,
                },
            },
        };

        return (
            <div>
            <Form onSubmit={this.handleSubmit}>
                <FormItem
                    {...formItemLayout}
                    label="Start time"
                >
                    {getFieldDecorator('startTime', {
                        rules: [
                            {required: true, message: 'Please input start time.'}
                        ],
                    })(
                        <DatePicker showTime format="YYYY-MM-DD HH:mm:ss" />
                    )}

                </FormItem>

                <FormItem
                    {...formItemLayout}
                    label="First team"
                >
                    {getFieldDecorator('firstTeamId', {
                        rules: [
                            {required: true, message: 'Please select team.'},
                            {validator: this.checkVariousTeams}
                        ],
                    })(
                        <Select placeholder="Select team">
                            {this.state.teams.map((team, index) => {
                                return <Option value={team.id} key={team.id}>{team.name}</Option>;
                            })}
                        </Select>
                    )}
                </FormItem>

                <FormItem
                    {...formItemLayout}
                    label="Second team"
                >
                    {getFieldDecorator('secondTeamId', {
                        rules: [
                            {required: true, message: 'Please select team.'},
                            {validator: this.checkVariousTeams}
                        ],
                    })(
                        <Select placeholder="Select team">
                            {this.state.teams.map((team, index) => {
                                return <Option value={team.id} key={team.id}>{team.name}</Option>;
                            })}
                        </Select>
                    )}
                </FormItem>

                <FormItem {...tailFormItemLayout}>
                    <Link to={'/games'}><Button style={{marginRight: 1 + 'em'}}
                            type="secondary"
                    >Back</Button></Link>
                    <Button type="primary" htmlType="submit">Create</Button>
                </FormItem>
            </Form>

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

const WrappedCreateGame = Form.create()(GameCreateContainer);

export default connect( mapStateToProps, mapDispatchToProps )(WrappedCreateGame);