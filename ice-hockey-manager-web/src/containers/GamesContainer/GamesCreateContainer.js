import React, {Component} from 'react';

import axios from '../../axios';
import {Link, Redirect} from 'react-router-dom';

import {Form, Input, Select, Button, InputNumber} from 'antd';

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
//        e.preventDefault();
//        this.props.form.validateFieldsAndScroll((err, values) => {
//            if (!err) {
//                console.log("post values "+ values);
//                axios.post('/games/create', values)
//                    .then(response => {
//                        console.log("response"+ response);
//                        this.setState({
//                            redirect: true
//                        });
//                    })
//                    .catch(error => {
//                        console.log(error);
//                    });
//            }
//        });
    }

    handleConfirmBlur(e) {
        const value = e.target.value;
        this.setState({confirmDirty: this.state.confirmDirty || !!value});
    }

    getTeam = (teamId) => {
        axios.get('/teams/' + teamId)
            .then(response => {
        console.log(response.data);
                return response.data;
            })
            .catch(error => {
                console.log(error);
            });
    }

    render() {
        if (this.state.redirect){
           return <Redirect to="/games"/>;
        }
//        const {getFieldDecorator} = this.props.form;

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
        let initValueTeam;
        if (this.state.teams[0] !== undefined) {
            initValueTeam = this.state.teams[0].id;
        }
        return (
            <Form onSubmit={this.handleSubmit}>
                <FormItem
                    {...formItemLayout}
                    label="Start time"
                >
                    <Input/>

                </FormItem>

                <FormItem
                    {...formItemLayout}
                    label="First team"
                >

                    <Select>
                        {this.state.teams.map((team, index) => {
                            return <Option value={this.getTeam(team.id)} key={index}>{team.name}</Option>;
                        })}
                    </Select>
                </FormItem>

                <FormItem
                    {...formItemLayout}
                    label="Second team"
                >

                    <Select>
                        {this.state.teams.map((team, index) => {
                            return <Option value={this.getTeam(team.id)} key={index}>{team.name}</Option>;
                        })}
                    </Select>
                </FormItem>

                <FormItem {...tailFormItemLayout}>
                    <Button type="primary" htmlType="submit">Create</Button>
                </FormItem>
            </Form>
        );
    }
}

const WrappedCreateTeam = Form.create()(GameCreateContainer);

export default GameCreateContainer;