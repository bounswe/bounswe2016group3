import React, { Component } from 'react';
import {
  Modal,
  ModalHeader,
  ModalTitle,
  ModalClose,
  ModalBody,
  ModalFooter
} from 'react-modal-bootstrap';

class ModalTest extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <Modal isOpen={this.props.isOpen} onRequestHide={this.props.hideModal}>
        <ModalHeader>
          <ModalClose onClick={this.props.hideModal}/>
          <ModalTitle>Bakalim oldun mu :P</ModalTitle>
        </ModalHeader>
        <ModalBody>
          <p>Nothing would be there // URL or File ???</p>
          </ModalBody>
          <ModalFooter>
            <button className='btn btn-default' onClick={this.props.hideModal}>
              Close
            </button>
            <button className='btn btn-primary'>
              Save changes
            </button>
          </ModalFooter>
        </Modal>
      );
  }

}


class PicEdit extends Component {
  constructor() {
    super();
    this.state = {
      isOpen: false
    };
    this.openModal = this.openModal.bind(this);
    this.hideModal = this.hideModal.bind(this);
  }

  openModal() {
    this.setState({
      isOpen: true
    });
  }

  hideModal() {
    this.setState({
      isOpen: false
    });
  }

  render() {
    return (
      <div>
        <button type="button" className="btn btn-default" onClick={this.openModal}>
          Edit Picture
        </button>
        <ModalTest
          isOpen={this.state.isOpen}
          hideModal={this.hideModal}
        />
      </div>
    );
  }
}

export default PicEdit;
