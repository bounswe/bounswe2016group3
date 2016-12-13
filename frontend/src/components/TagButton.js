import React, { Component } from 'react';

import {
  Modal,
  ModalHeader,
  ModalTitle,
  ModalClose,
  ModalBody,
  ModalFooter
} from 'react-modal-bootstrap';


class MealTags extends Component {
  constructor() {
    super();
    this.state = {
      tags: ['banana', 'apple', 'go', 'nine', 'six', 'seven']
    };

    this.deleteTag = this.deleteTag.bind(this);
  }

  deleteTag() {
    console.log('Good!!!');
  }

  render() {
    return (
      <ul className="list-group">
        {this.state.tags.map((tag) => (
          <div key={tag}>
            <button class="link" onClick={this.deleteTag}> &#x2717; </button>
            <li className="list-group-item list-group-item-info">{tag}</li>
          </div>
          ))}
      </ul>
    );
  }
}

class ModalTags extends Component {
  render() {
    return (
      <Modal isOpen={this.props.isOpen} onRequestHide={this.props.close}>
        <ModalHeader>
          <ModalClose onClick={this.props.close}/>
          <ModalTitle>Tags</ModalTitle>
        </ModalHeader>
        <ModalBody>
          <MealTags />
        </ModalBody>
        <ModalFooter>
          <div>
            <button className='btn btn-primary'>
              Save changes
            </button>
            <button className='btn btn-default' onClick={this.props.close}>
              Close
            </button>
          </div>
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
