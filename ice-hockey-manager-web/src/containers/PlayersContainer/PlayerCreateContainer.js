import React, {Component} from 'react';
import {Redirect} from 'react-router-dom';
import axios from "../../axios";
import {transformPositionLabel} from "../../other/Helper";

import {Form, Input, Select, Button, InputNumber} from 'antd';

const FormItem = Form.Item;
const Option = Select.Option;

class PlayerCreateContainer extends Component {

    constructor(props) {
        super(props);
        this.state = {
            confirmDirty: false,
            redirect: false
        };
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    componentDidMount() {
        console.log("start of creating a player");
        this.forceUpdate();
    }

    handleSubmit(e) {
        console.log("handling submitted form");
        e.preventDefault();
        this.props.form.validateFieldsAndScroll((err, values) => {
            console.log("values: ", values);
            if (!err) {
                axios.put('/players/create', values)
                    .then(response => {
                        console.log("successfully added player");
                        console.log("response: ", response);
                        this.setState({
                            redirect: true
                        });
                    })
                    .catch(error => {
                        console.log(error);
                    });
            } else {
                console.log("error during player creation");
            }
        });
    }

    handleConfirmBlur(e) {
        console.log("blur confirm");
        const value = e.target.value;
        this.setState({confirmDirty: this.state.confirmDirty || !!value});
    }

    render() {
        if(this.state.redirect){
            console.log("redirecting from players");
            return <Redirect to="/players"/>;
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
            <Form onSubmit={this.handleSubmit}>
                <FormItem
                    {...formItemLayout}
                    label="Name"
                >
                    {getFieldDecorator('name', {
                        rules: [{
                            required: true, message: 'Please input player\'s full name!',
                        }],
                    })(
                        <Input/>
                    )}
                </FormItem>
                <FormItem
                    {...formItemLayout}
                    label="Position"
                >
                    {getFieldDecorator('position', {
                        rules: [
                            {required: true, message: 'Please select player\'s position!'},
                        ],
                        initialValue: 'CENTER'
                    })(
                        <Select>
                            <Option value="CENTER">{transformPositionLabel("CENTER")}</Option>
                            <Option value="LEFT_WING">{transformPositionLabel("LEFT_WING")}</Option>
                            <Option value="RIGHT_WING">{transformPositionLabel("RIGHT_WING")}</Option>
                            <Option value="DEFENSEMAN">{transformPositionLabel("DEFENSEMAN")}</Option>
                            <Option value="GOALKEEPER">{transformPositionLabel("GOALKEEPER")}</Option>
                        </Select>
                    )}
                </FormItem>
                <FormItem
                    {...formItemLayout}
                    label="Attack Skill"
                >
                    {getFieldDecorator('attSkill', {
                        rules: [
                            {required: true, message: 'Please set player\'s attack skill!'},
                        ],
                        initialValue: "50"
                    })(
                        <InputNumber min={1} max={99}/>
                    )}
                </FormItem>
                <FormItem
                    {...formItemLayout}
                    label="Defense Skill"
                >
                    {getFieldDecorator('defSkill', {
                        rules: [
                            {required: true, message: 'Please set player\'s defense skill!'},
                        ],
                        initialValue: "50"
                    })(
                        <InputNumber min={1} max={99}/>
                    )}
                </FormItem>
                <FormItem
                    {...formItemLayout}
                    label="Price"
                >
                    {getFieldDecorator('price', {
                        rules: [
                            {required: true, message: 'Please set player\'s price!'},
                        ],
                        initialValue: "1"
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


const WrappedCreatePlayer = Form.create()(PlayerCreateContainer);

export default WrappedCreatePlayer;