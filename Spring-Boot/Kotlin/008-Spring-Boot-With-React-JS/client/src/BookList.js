import React, { Component } from 'react';
import {
  Alert,
  Button
} from 'reactstrap';
import { Link } from 'react-router-dom';

const Book = (props) => (
  <div className="book-container p-2 m-2 d-flex flex-column">
    <h3>{props.name}</h3>
    <div className="book-body">
      <div className="subtitle-container">
        <div>Name: {props.name} </div>
        <div>Genre: {props.genre} </div>
      </div>
    </div>
    <div className="book-footer">
      <Button color="secondary" tag={Link} to={'/book-list/' + props.id}>Edit</Button>
      <Button color="danger" onClick={() => props.remove(props.id)}>Delete</Button>
    </div>
  </div>
);

class BookList extends Component {

  constructor(props) {
    super(props);
    this.state = {
      books: [],
      isLoading: true,
      errorMessage: null
    };
    this.remove = this.remove.bind(this);
  }

  async componentDidMount() {
    this.setState({isLoading: true});
    const response = await this.props.api.getAll();
    if (!response.ok) {
      this.setState({
          errorMessage: `Failed to load Books: ${response.status} ${response.statusText}`,
          isLoading: false
        }
      )
    }
    else {
      const body = await response.json();
      const books = body._embedded.books;
      this.setState({
        books: books,
        isLoading: false,
        errorMessage: null
      });
    }
  }

  async remove(id) {
    let response = await this.props.api.delete(id);
    if (!response.ok) {
      this.setState({errorMessage: `Failed to delete book: ${response.status} ${response.statusText}`})
    }
    else {
      let updatedBooks = [...this.state.books].filter(i => i.id !== id);
      this.setState({books: updatedBooks, errorMessage: null});
    }
  }

  render() {
    const {books, isLoading, errorMessage} = this.state;

    if (isLoading) {
      return <p>Loading...</p>;
    }

    return (
      <div>
        {this.props.navbar}
        <div className="d-flex flex-row justify-content-between p-3">
          <h3 className="book-list-title">Books</h3>
          <Button color="success" tag={Link} to="/book-list/new">Add New</Button>
        </div>
        {errorMessage ?
          <div className="d-flex flex-row justify-content-center">
            <Alert color="warning" style={{flex:1, maxWidth:'80%'}}>
              {errorMessage}
            </Alert>
          </div> : null
        }
        <div className="d-flex flex-row flex-container flex-wrap justify-content-center">
          {books.map( book =>
            <Book {...book} remove={this.remove.bind(this)} key={book.id}/>
          )}
          {!books || books.length === 0 ? <p>No books!</p> : null}
        </div>
      </div>
    );
  }
}

export default BookList;