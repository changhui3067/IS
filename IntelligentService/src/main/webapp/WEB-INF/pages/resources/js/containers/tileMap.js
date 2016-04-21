/**
 * Created by freyjachang on 4/21/16.
 */
import { connect } from 'react-redux'
import { handleTileClick } from '../actions/headerAction'
import Tile from './../components/tile'
import { getUsername, getUserphoto, getUsertype} from '../reducers/employee'
import { getTileUnread} from '../reducers/tilesInfo'

const mapStateToProps = (state, ownProps) => {
    return {
        username: getUsername(state),
        userphoto: getUserphoto(state),
        usertype: getUsertype(state),
        title: ownProps.title,
        name: ownProps.name,
        background: ownProps.background,
        unreadCount: getTileUnread(state, ownProps.name),
        icon: ownProps.icon,
        type: ownProps.tileType,
        url: ownProps.url
    };
}

const mapDispatchToProps = (dispatch, ownProps) => {
    return {
        onClick: (menuItem) => {
            console.log("click dispatch: handleTileClick");
            dispatch(handleTileClick(menuItem))
        }
    };
}

const TileMap = connect(
    mapStateToProps,
    mapDispatchToProps
)(Tile)



export default TileMap;