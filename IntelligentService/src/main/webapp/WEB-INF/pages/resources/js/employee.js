/**
 * Created by Xc on 2016/4/2.
 */

var course = function (courseId, courseName, picSrc, description) {
    return {
        "courseId": courseId,
        "courseName": courseName,
        "picSrc": picSrc,
        "description": description
    };
}


var Course = React.createClass({
    render: function () {
        return (
            <div className="courseItem container">
                <img className="col-md-4" src={this.props.picSrc}/>
                <div className="col-md-8">
                    <h3 className="course_title">{this.props.title}</h3>
                    <div className="course_description">{this.props.description}</div>
                    <h5 className="course_continue"><a href="javascript:void(0)">Start Course</a></h5>
                </div>
            </div>
        );
    }
});

var CourseList = React.createClass({
    render: function () {
        var courses = [];
        this.props.courseList.forEach(function (course) {
            courses.push(
                <Course picSrc={course.picSrc} title={course.courseName} description={course.description}/>
            );
        });
        return (
            <div>
                {courses}
            </div>
        );
    }
});


var mycourse = function () {
    //$.ajax("/course_list")
    $('#mycourse').addClass("selected");
    var courseList = [new course(1, "Learn React", "/resources/img/folder.png", "This is a course about react"),
        new course(1, "Learn React", "/resources/img/folder.png", "This is a course about react"),
        new course(1, "Learn React", "/resources/img/folder.png", "This is a course about react")];
    ReactDOM.render(
        <CourseList courseList={courseList}/>,
        document.getElementById("main_panel")
    );
}

var employee = function (name, picSrc) {
    return {
        "name": name,
        "picSrc": picSrc
    };
}

var EmpCard = React.createClass({
    render: function () {
        return (
            <div className="emp_card">
                <img src={this.props.picSrc}/>
                <h5>{this.props.name}</h5>
            </div>
        )
    }
});

var Orgchart = React.createClass({
    render: function () {
        var otherEmps = [];
        var emps = [];

        this.props.bossOtherEmployees.forEach(
            function (employee) {
                otherEmps.push(<li><a href="#"><EmpCard{...employee}/></a></li>);
            }
        );
        this.props.employees.forEach(
            function (employee) {
                emps.push(<li><a href="#"><EmpCard{...employee}/></a></li>);
            }
        );

        return (
            <div className="tree">
                <ul>
                    <li>
                        <a href="#"><EmpCard {...this.props.boss}/></a>
                        <ul>
                            {otherEmps}
                            <li>
                                <a href="#"><EmpCard {...this.props.user}/></a>
                                <ul>
                                    {emps}
                                </ul>
                            </li>
                        </ul>
                    </li>

                </ul>
            </div>
        )
    }
});


var orgchart = function () {
    $('#orgchart').addClass("selected");
    var testPicSrc = "/resources/img/folder.png";
    var boss = new employee("BOSS", testPicSrc);
    var user = new employee("Alex San", testPicSrc);
    var bossOtherEmployees = [new employee("Bill", testPicSrc), new employee("Helen", testPicSrc), new employee("Jacky", testPicSrc)];
    var employees = [new employee("Frank", testPicSrc), new employee("July", testPicSrc), new employee("Jane", testPicSrc), new employee("Lucy", testPicSrc)];
    ReactDOM.render(
        <Orgchart user={user} boss={boss} bossOtherEmployees={bossOtherEmployees} employees={employees}/>,
        document.getElementById("main_panel")
    );
}



$('#mycourse').on('click', mycourse);
$('#open_position').on('click', mycourse );
$('#orgchart').on('click', orgchart);
mycourse();
