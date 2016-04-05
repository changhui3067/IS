/**
 * Created by Xc on 2016/4/5.
 */

var notificationData = function (content) {
    return {
        "content": content
    }
}


var Notification = React.createClass({
    render: function () {
        return (
            <div>{this.props.content}</div>
        );
    }
});


var NotificationPanel = React.createClass({

    render: function () {
        var notifications = this.props.notifcations.map(
            function (notification) {
                return (
                    <tr><td><Notification {...notification}/></td></tr>
                );
            }
        );
        return (
            <table className="table">
                {notifications}
            </table>
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
    <NotificationPanel notifcations={notificationList}/>,
    document.getElementById("notification")
);