import React, { Component } from 'react';

import {
  Modal,
  ModalHeader,
  ModalTitle,
  ModalClose,
  ModalBody,
  ModalFooter
} from 'react-modal-bootstrap';

class ModalTags extends Component {
  render() {
    return (
      <Modal isOpen={this.props.isOpen} onRequestHide={this.props.close}>
        <ModalHeader>
          <ModalClose onClick={this.props.close}/>
          <ModalTitle>Tags</ModalTitle>
        </ModalHeader>
        <ModalBody>
          Deneyelim Bakalim
        </ModalBody>
        <ModalFooter>
          <button className='btn btn-default' onClick={this.props.close}>
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

class TagButton extends Component {
  componentDidMount() {
    console.log('Should be put there a viewTag call!');
  }


  constructor(props) {
    super(props);
    this.state = {
      isOpen: false
    };

    this.viewTags = this.viewTags.bind(this);
    this.closeTags = this.closeTags.bind(this);
  }

  viewTags() {
    this.setState({isOpen: true});
  }

  closeTags() {
    this.setState({isOpen: false});
  }

  render() {
    return (
      <div>
        <button type="button" onClick={this.viewTags}
          className="btn btn-default">Tags</button>
        <ModalTags isOpen={this.state.isOpen} close={this.closeTags} />
      </div>
    );
  }
}

export default TagButton;
