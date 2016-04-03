var LoginForm = React.createClass({
    render: function () {
        return (
            <div>
                <form>
                    <input/>
                    <input/>
                    <button type="submit">Login</button>
                </form>
            </div>
        );
    }
});
ReactDOM.render(
    <LoginForm/>,
    document.getElementById("content")
);