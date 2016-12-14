import React, { Component } from 'react';

import {
  Modal,
  ModalHeader,
  ModalTitle,
  ModalClose,
  ModalBody,
  ModalFooter
} from 'react-modal-bootstrap';

class SearchBar extends Component {
  constructor() {
    super();
    this.state = {
      query_tags: ['go', 'over', 'here', 'to', 'try', ':P'],
      results: []
    };

    this.tagConstruct = this.tagConstruct.bind(this);
    this.search = this.search.bind(this);
  }

  tagConstruct(tag) {
    //construct a new tag apiCall should be added...
    this.setState({
      query_tags: [...this.state.query_tags, tag]
    });
    this.props.addTag(tag);
  }

  search() {
    const query = this.input.value;
    var results = [];
    if (query === "") {
      this.setState({
        results: []
      });
      return;
    }
    this.state.query_tags.forEach((tag) => {
      if(tag.indexOf(query) === -1 ||
        this.props.tags.includes(tag))  return;
      results.push(tag);
    });
    this.setState({
      results: results
    });
  }


  render(){
    const rows = this.state.results.map(result =>
       <li className="list-group-item" key={result}>{result}</li>);
    return (
      <div>
        <input type="text" id="TagInput"
          ref={(input)=> this.input = input} onChange={this.search}
          placeholder="Search Tag..." />
        <ul className="list-group">
          {rows}
        </ul>
      </div>
    )
  }
}


class MealTags extends Component {
  // tags should be a prop getting from reducers.
  // Actions for mealTags should be added...
  constructor() {
    super();
    this.state = {
      tags: ['banana', 'apple', 'go', 'nine', 'six', 'seven'],
    };

    this.deleteTag = this.deleteTag.bind(this);
    this.addTag = this.addTag.bind(this);
  }

  addTag(tag) {
    // should be changed to addTag apiCall
    this.setState({
      tags: [...this.state.tags, tag]
    });
  }

  deleteTag() {
    console.log('Good!!!');
    console.log('DeleteTag call should be added.');
  }

  render() {
    return (
      <div>
        <SearchBar tags={this.state.tags} addTag={this.addTag}/>
        <ul className="list-group">
          {this.state.tags.map((tag) => (
            <div key={tag}>
              <button className="link" onClick={this.deleteTag}> &#x2717; </button>
              <li className="list-group-item list-group-item-info">{tag}</li>
            </div>
          ))}
        </ul>
      </div>
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
