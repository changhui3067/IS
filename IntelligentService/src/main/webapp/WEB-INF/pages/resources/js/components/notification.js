/**
 * Created by Xc on 2016/4/5.
 */

var notificationData = function (content) {
    return {
        "content": content
    }
};



var NotificationPanel = React.createClass({
    render: function () {
        var wrapper = this.props.elementWrapper;
        var notifications = this.props.notifcations.map(
            function (notification) {
                return (
                    React.createElement(wrapper, null  ,notification.content)
                    //<Notification {...notification} elementWrapper={this.props.elementWrapper}/>
                );
            }
        );
        return (
            React.createElement(this.props.panelWrapper,{...this.props},notifications)
        );
    }
});


var notificationList = [];
notificationList.push(new notificationData("This is a notification"));
notificationList.push(new notificationData("This is a notification"));
notificationList.push(new notificationData("This is a notification"));
notificationList.push(new notificationData("This is a notification"));
notificationList.push(new notificationData("This is a notification"));
notificationList.push(new notificationData("This is a notification"));
notificationList.push(new notificationData("This is a notification"));
notificationList.push(new notificationData("This is a notification"));
notificationList.push(new notificationData("This is a notification"));
notificationList.push(new notificationData("This is a notification"));

ReactDOM.render(
    <NotificationPanel panelWrapper="ul" elementWrapper="li" className="dropdown-menu" notifcations={notificationList}/>,
    document.getElementById("notification")
);