import React, { useEffect, useState } from 'react';
// import { withRouter } from 'react-router-dom';
import { Link, useParams, useLocation, useNavigate, useMatch } from 'react-router-dom';
import { Alert, Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
/*
export function withRouter(Children){
    return(props)=>{
        const location = useLocation();
        const params = useParams();
        const navigate = useNavigate();
        const match = {params: useParams()};
        const history = {
            back: () => navigate(-1),
            goBack: () => navigate(-1),
            location,
            push: (url: string, state?: any) => navigate(url, { state }),
            replace: (url: string, state?: any) => navigate(url, {
                replace: true,
                state
                })
            };
        return <Children {...props}
            location = {location}
            params = {params}
            navigate = {navigate}
            match = {match}
            history = {history}
            />
    }

}
*/
function BookEdit() {
  let emptyItem = {
    name: '',
    genre: ''
  };

    const [item, setItem] = useState(emptyItem);
    const [errorMessage, setErrorMessage] = useState();
    const [isCreate, setIsCreate] = useState(false);

  useEffect(async () => {
 //   const { id } = useParams();
//    console.log(this.props);
    console.log(this.props.match);
//    console.log(this.props.match.params);
    console.log(this.props.match.params.id);
//    const params = useParams();
//        this.state.isCreate = this.props.params.id === 'new';
      this.state.isCreate = this.props.params.id === 'new'; // are we editing or creating?
    if (!this.state.isCreate) {
//          const response = await this.props.api.getById(this.props.params.id);
        const response = await this.props.api.getById(this.props.match.params.id);
      const book = await response.json();
      this.setState({item: book});
    }
  }, []);

  const handleChange = (event) => {
    const target = event.target;
    const value = target.value;
    const name = target.name;
    let item = {...this.state.item};
    item[name] = value;
    this.setState({item});
  }

  const handleSubmit = async (event) => {
    event.preventDefault();
    const {item, isCreate} = this.state;
//    const navigate = useNavigate();

    let result = isCreate ? await this.props.api.create(item) : await this.props.api.update(item);

    if (!result.ok) {
      this.setState({errorMessage: `Failed to ${isCreate ? 'create' : 'update'} record: ${result.status} ${result.statusText}`})
    } else {
      this.setState({errorMessage: null});
      this.props.history.push('/book-list');
//    this.props.navigation.navigate('/book-list');
    }

  }

    const title = <h2>{isCreate ? 'Add Book' : 'Edit Book'}</h2>;

    return (
      <div>
        {this.props.navbar}
        <Container style={{textAlign: 'left'}}>
          {title}
          {errorMessage ?
            <Alert color="warning">
              {errorMessage}
            </Alert> : null
          }
          <Form onSubmit={this.handleSubmit}>
            <div className="row">
              <FormGroup className="col-md-8 mb-3">
                <Label for="name">Name</Label>
                <Input type="text" name="name" id="name" value={item.name || ''}
                       onChange={this.handleChange} autoComplete="name"/>
              </FormGroup>
              <FormGroup className="col-md-4 mb-3">
                <Label for="genre">Genre</Label>
                <Input type="text" name="genre" id="genre" value={item.genre || ''}
                       onChange={this.handleChange} autoComplete="genre"/>
              </FormGroup>
            </div>
            <FormGroup>
              <Button color="primary" type="submit">Save</Button>{' '}
              <Button color="secondary" tag={Link} to="/book-list">Cancel</Button>
            </FormGroup>
          </Form>
        </Container>
      </div>
    );
}

export default BookEdit;


/*
function withRouter(Component) {
    function ComponentWithRouter(props) {
        const location = useLocation();
        const params = useParams();
        const navigate = useNavigate();
//        const match = useMatch("/book-list/:id");
        const match = { params: useParams() };
        const history = {
            back: () => navigate(-1),
            goBack: () => navigate(-1),
            location,
            push: (url: string, state?: any) => navigate(url, { state }),
            replace: (url: string, state?: any) => navigate(url, {
              replace: true,
              state
            })
        };

        return (
            <Component
                {...props}
                router={{location, navigate, params}}
                history={history}
                location={location}
                params={params}
                navigate={navigate}
                match={match}
            />
        );
    }
    return ComponentWithRouter
}
*/

/*
class BookEdit extends React.Component {
  emptyItem = {
    name: '',
    genre: ''
  };

  constructor(props) {
    super(props);
    this.state = {
      item: this.emptyItem,
      errorMessage: null,
      isCreate: false
    };
    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  async componentDidMount() {
 //   const { id } = useParams();
//    console.log(this.props);
    console.log(this.props.match);
//    console.log(this.props.match.params);
    console.log(this.props.match.params.id);
//    const params = useParams();
//        this.state.isCreate = this.props.params.id === 'new';
      this.state.isCreate = this.props.params.id === 'new'; // are we editing or creating?
    if (!this.state.isCreate) {
//          const response = await this.props.api.getById(this.props.params.id);
        const response = await this.props.api.getById(this.props.match.params.id);
      const book = await response.json();
      this.setState({item: book});
    }
  }

  handleChange(event) {
    const target = event.target;
    const value = target.value;
    const name = target.name;
    let item = {...this.state.item};
    item[name] = value;
    this.setState({item});
  }

  async handleSubmit(event) {
    event.preventDefault();
    const {item, isCreate} = this.state;
//    const navigate = useNavigate();

    let result = isCreate ? await this.props.api.create(item) : await this.props.api.update(item);

    if (!result.ok) {
      this.setState({errorMessage: `Failed to ${isCreate ? 'create' : 'update'} record: ${result.status} ${result.statusText}`})
    } else {
      this.setState({errorMessage: null});
      this.props.history.push('/book-list');
//    this.props.navigation.navigate('/book-list');
    }

  }

  render() {
    const {item, errorMessage, isCreate} = this.state;
    const title = <h2>{isCreate ? 'Add Book' : 'Edit Book'}</h2>;

    return (
      <div>
        {this.props.navbar}
        <Container style={{textAlign: 'left'}}>
          {title}
          {errorMessage ?
            <Alert color="warning">
              {errorMessage}
            </Alert> : null
          }
          <Form onSubmit={this.handleSubmit}>
            <div className="row">
              <FormGroup className="col-md-8 mb-3">
                <Label for="name">Name</Label>
                <Input type="text" name="name" id="name" value={item.name || ''}
                       onChange={this.handleChange} autoComplete="name"/>
              </FormGroup>
              <FormGroup className="col-md-4 mb-3">
                <Label for="genre">Genre</Label>
                <Input type="text" name="genre" id="genre" value={item.genre || ''}
                       onChange={this.handleChange} autoComplete="genre"/>
              </FormGroup>
            </div>
            <FormGroup>
              <Button color="primary" type="submit">Save</Button>{' '}
              <Button color="secondary" tag={Link} to="/book-list">Cancel</Button>
            </FormGroup>
          </Form>
        </Container>
      </div>
    );
  }
}

const HOCBookEdit = withRouter(BookEdit);

export default HOCBookEdit;
*/