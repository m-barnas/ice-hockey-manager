import React, {Component} from 'react';

import axios from '../../axios';
import {Redirect} from 'react-router-dom';
import {transformCountryLabel} from '../../other/Helper';

import {Form, Input, Select, Button, InputNumber} from 'antd';

const FormItem = Form.Item;
const Option = Select.Option;


class TeamCreateContainer extends Component {

    constructor(props) {
        super(props);
        this.state = {
            confirmDirty: false,
            managers: [],
            redirect: false

        };
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    componentDidMount() {
        axios.get('/managers/all')
            .then(response => {
                this.setState({
                    managers: response.data,
                });
            })
            .catch(error => {
                console.log(error);
            });
    }

    handleSubmit(e) {
        console.log("team - handling submitted form");
        e.preventDefault();
        this.props.form.validateFieldsAndScroll((err, values) => {
            console.log("values: ", values);
            if (!err) {
                axios.put('/teams/create', values)
                    .then(response => {
                        console.log("successfully added team");
                        console.log("response: ", response);
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


    render() {
        if(this.state.redirect){
           return <Redirect to="/teams"/>;
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
        let initValueManager;
        if (this.state.managers[0] !== undefined) {
            initValueManager = this.state.managers[0].id;
        }
        return (
            <Form onSubmit={this.handleSubmit}>
                <FormItem
                    {...formItemLayout}
                    label="Name"
                >
                    {getFieldDecorator('name', {
                        rules: [{
                            required: true, message: 'Please input your team name!',
                        }],
                    })(
                        <Input/>
                    )}
                </FormItem>
                <FormItem
                    {...formItemLayout}
                    label="Country"
                >
                    {getFieldDecorator('competitionCountry', {
                        rules: [
                            {required: true, message: 'Please select your country!'},
                        ],
                        initialValue: 'CZECH_REPUBLIC'
                    })(
                        <Select>
                            <Option value="CZECH_REPUBLIC">{transformCountryLabel("CZECH_REPUBLIC")}</Option>
                            <Option value="ENGLAND">{transformCountryLabel("ENGLAND")}</Option>
                            <Option value="SLOVAKIA">{transformCountryLabel("SLOVAKIA")}</Option>
                            <Option value="GERMANY">{transformCountryLabel("GERMANY")}</Option>
                            <Option value="FINLAND">{transformCountryLabel("FINLAND")}</Option>
                            <Option value="FRANCE">{transformCountryLabel("FRANCE")}</Option>
                        </Select>
                    )}
                </FormItem>
                <FormItem
                    {...formItemLayout}
                    label="Manager"

                >
                    {getFieldDecorator('humanPlayerId', {
                        rules: [
                            {required: true, message: 'Please select your manager!'},
                        ],
                        initialValue: initValueManager
                    })(
                        <Select>
                            {this.state.managers.map(function (manager, index) {
                                return <Option value={manager.id} key={index}>{manager.username}</Option>;
                            })}
                        </Select>
                    )}
                </FormItem>
                <FormItem
                    {...formItemLayout}
                    label="Budget"

                >
                    {getFieldDecorator('budget', {
                        rules: [
                            {required: true, message: 'Please set your budget!'},
                        ],
                        initialValue: "100"
                    })(
                        <InputNumber min={1}/>
                    )}
                </FormItem>

                <FormItem {...tailFormItemLayout}>
                    <Button type="primary" htmlType="submit">Create</Button>
                </FormItem>
            </Form>
        );
    }

}

const WrappedCreateTeam = Form.create()(TeamCreateContainer);

export default WrappedCreateTeam;