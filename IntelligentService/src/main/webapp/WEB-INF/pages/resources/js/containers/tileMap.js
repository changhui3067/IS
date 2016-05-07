/**
 * Created by freyjachang on 4/21/16.
 */
import { connect } from 'react-redux'
import { handleTileClick } from '../actions/headerAction'
import Tile from './../components/tile'
import { getUsername, getUserphoto, getUsertype} from '../reducers/employee'

const mapStateToProps = (state, ownProps) => {
    return {
        username: getUsername(state),
        userphoto: getUserphoto(state),
        usertype: getUsertype(state),
        title: ownProps.title,
        name: ownProps.name,
        background: ownProps.background,
        icon: ownProps.icon,
        type: ownProps.type,
        url: ownProps.url
    };
}

const mapDispatchToProps = (dispatch, ownProps) => {
    return {
        onClick: (menuItem) => {
            dispatch(handleTileClick(menuItem))
        }
    };
}

const TileMap = connect(
    mapStateToProps,
    mapDispatchToProps
)(Tile)



export default TileMap;