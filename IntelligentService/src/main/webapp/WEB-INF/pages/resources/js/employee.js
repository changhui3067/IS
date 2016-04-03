/**
 * Created by Xc on 2016/4/2.
 */

var course = function (courseId, courseName, picSrc) {
    return {
        "courseId": courseId,
        "courseName": courseName,
        "picSrc": picSrc
    };
}

var mycourse = function () {
    //$.ajax("/course_list")
    var courseList = [new course(1, "course1", "/"),
        new course(1, "course1", "/"),
        new course(1, "course1", "/")];
    $("#main_panel").html(renderCourseList(courseList));
}

var renderCourseList = function (courseList){
    var html = "<div>";
    for(var course in courseList){
        html += "<div class='courseItem'><img src='https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/logo_white_fe6da1ec.png'/></div>"
    }
    html += "</div>";
    return html;
}