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
        var courses = []
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
    var courseList = [new course(1, "Learn React", "/resources/img/folder.png", "This is a course about react"),
        new course(1, "Learn React", "/resources/img/folder.png", "This is a course about react"),
        new course(1, "Learn React", "/resources/img/folder.png", "This is a course about react")];
    ReactDOM.render(
        <CourseList courseList={courseList}/>,
        document.getElementById("main_panel")
    );
}
$('#mycourse').on('click', mycourse);

var renderCourseList = function (courseList) {
    var html = "<div>";
    for (var course in courseList) {
        html += "<div className='courseItem'><img src='https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/logo_white_fe6da1ec.png'/></div>"
    }
    html += "</div>";
    return html;
}