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
      results: [],
    };

    this.tagConstruct = this.tagConstruct.bind(this);
    this.search = this.search.bind(this);
    this.htmlQueryTags = this.htmlQueryTags.bind(this);
  }

  tagConstruct(e) {
    e.preventDefault();
    const tag = this.input.value;
    //construct a new tag apiCall should be added...
    this.setState({
      query_tags: [...this.state.query_tags, tag]
    });
    this.props.addTag(e, tag);
    this.input.value = "";
  }

  search() {
    // BUG: when adding a found query it should be deleted...
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

  htmlQueryTags() {

    if(!this.input || !this.input.value) return;
    const value = this.input.value;
    if(this.state.results.length === 0 && !this.props.tags.includes(value)) {
      return <li className="list-group-item" key={value}>
        {value} <button type="button" className="badge"
          onClick={this.tagConstruct}> Add Tag </button>
      </li>;
    } else {
      // Key part should change to values so that JS runs faster...
      return this.state.results.map(result =>
        <button className="list-group-item"
          onClick={(e) => { this.props.addTag(e, result);
          this.input.value=""; }}
          key={result}>{result}</button>);
    }
  }

  render(){
    const rows = this.htmlQueryTags();
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

  addTag(e, tag) {
    e.preventDefault();
    // should be changed to addTag apiCall
    this.setState({
      tags: [...this.state.tags, tag]
    });
  }

  deleteTag(e, tag) {
    e.preventDefault();
    console.log('Good!!!');
    console.log('DeleteTag call should be added.');
    const arr = this.state.tags;
    const index = arr.indexOf(tag);
    arr.splice(index, 1);
    this.setState({
      tags: arr
    });
  }

  render() {
    return (
      <div>
        <SearchBar tags={this.state.tags} addTag={this.addTag}/>
        <ul className="list-group">
          {this.state.tags.map((tag) => (
            <div key={tag}>
              <button className="link"
                onClick={(e) => this.deleteTag(e, tag)}> &#x2717; </button>
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
