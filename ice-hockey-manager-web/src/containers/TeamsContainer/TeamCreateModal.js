import React, {Component} from 'react';
import {Modal, Button, Form, Input, InputNumber} from 'antd';

const FormItem = Form.Item;

class TeamCreateModal extends Component {
    constructor(props) {
        super(props);
        this.state = {visible: false};
        this.showModal = this.showModal.bind(this);
        this.handleOk = this.handleOk.bind(this);
        this.handleCancel = this.handleCancel.bind(this);
    }

    showModal() {
        this.setState({
            visible: true,
        });
    }

    handleOk(e) {
        console.log(e);
        this.props.form.validateFields((err, values) => {
            if (!err) {
                console.log('Received values of form: ', values);
            }
        });
        this.setState({
            visible: false,
        });
    }

    handleCancel(e) {
        console.log(e);
        this.setState({
            visible: false,
        });
    }

    render() {
        return (
            <div>
                <Button type="primary" onClick={this.showModal}>Create new Team</Button>
                <Modal
                    title="Create new team"
                    visible={this.state.visible}
                    onOk={this.handleOk}
                    onCancel={this.handleCancel}
                    okText={"Create"}
                >
                    <Form layout="inline">
                        <FormItem>
                              <span>Name</span><Input type="text" placeholder="Name"/>
                        </FormItem>
                        <FormItem>
                            <span>Competition country</span><Input type="text" placeholder="Name"/>
                        </FormItem>
                        <FormItem>
                            <span>Human player</span><Input type="text" placeholder="Name"/>
                        </FormItem>
                        <FormItem>
                            <span>Budget</span><InputNumber min={1} max={10} defaultValue={3} />
                        </FormItem>
                    </Form>
                </Modal>
            </div>
        );
    }
}

export default TeamCreateModal;