import React from "react";
import "./../../css/orgchart.scss";
import ReactDOM from "react-dom";
import $ from "jquery";

const EmpCard = ({username, userphoto}) => {
    let logoImage = new Image()
    logoImage.src = require("./../../img/"+ userphoto)
    let background = {
        backgroundImage: 'url("'+ logoImage.src+'")'
    }

    return (
        <div className="empCardContainer" style={background} title={username}>
            <div className="empName"><p>{username}</p></div>
        </div>
    )
}

const Line = ({number}) => {
    return (
        <div></div>
    )
}

export default class Orgchart extends React.Component{

    render() {
        return (
            <div id="orgchartContent">
                <div className="manager">
                    <EmpCard {...this.props.manager}/>
                </div>
                <canvas id="lineMC" className="line">
                    <Line number={this.props.coworkers.length} />
                </canvas>
                <div className="coworkers">
                {this.props.coworkers.map((emp, index) => {
                    return (<EmpCard key={index} {...emp}/>)
                })}
                </div>
                <canvas id="lineCS" className="line">
                    <Line number={this.props.subordinates.length} />
                </canvas>
                <div className="subordinates">
                {this.props.subordinates.map((emp, index) => {
                    return (<EmpCard key={index} {...emp}/>)
                })}
                </div>
            </div>
        );
    }

    componentDidMount() {
        this.paintByCanvas()
    }

    paintByCanvas(){
        var EMPCARD_WIDTH = 100;
        var EMPCARD_PADDING = 20;
        var CANVAS_HEIGHT = 40;
        var lengthMC = this.props.coworkers.length > 4 ? 4 : this.props.coworkers.length;
        var cvsMC = document.getElementById("lineMC")
        cvsMC.width = cvsMC.clientWidth
        cvsMC.height = cvsMC.clientHeight
        var ctxMC = cvsMC.getContext('2d')
        ctxMC.lineWidth = 1
        ctxMC.strokeStyle = 'rgba(0, 0, 0, 1)'
        ctxMC.moveTo(EMPCARD_WIDTH/2, 0)
        ctxMC.lineTo(EMPCARD_WIDTH/2, CANVAS_HEIGHT)
        ctxMC.moveTo(EMPCARD_WIDTH/2, CANVAS_HEIGHT/2)
        ctxMC.lineTo(EMPCARD_WIDTH*(lengthMC*2-1)/2+(lengthMC-1)*EMPCARD_PADDING, CANVAS_HEIGHT/2)
        for(var i=1; i<lengthMC; i++){
            ctxMC.moveTo(EMPCARD_WIDTH*(i*2+1)/2+EMPCARD_PADDING*i, CANVAS_HEIGHT/2)
            ctxMC.lineTo(EMPCARD_WIDTH*(i*2+1)/2+EMPCARD_PADDING*i, CANVAS_HEIGHT)
        }
        ctxMC.stroke()

        var lengthCS = this.props.subordinates.length > 4 ? 4 : this.props.subordinates.length;
        var cvsCS = $('#lineCS').get(0)
        cvsCS.width = cvsCS.clientWidth
        cvsCS.height = cvsCS.clientHeight
        var ctxCS = cvsCS.getContext('2d')
        //ctxCS.beginPath()
        ctxCS.lineWidth = 1
        ctxCS.strokeStyle = 'rgba(0, 0, 0, 1)'
        ctxCS.moveTo(EMPCARD_WIDTH/2, 0)
        ctxCS.lineTo(EMPCARD_WIDTH/2, CANVAS_HEIGHT)
        ctxCS.moveTo(EMPCARD_WIDTH/2, CANVAS_HEIGHT/2)
        ctxCS.lineTo(EMPCARD_WIDTH*(lengthCS*2-1)/2+(lengthCS-1)*EMPCARD_PADDING, CANVAS_HEIGHT/2)
        for(var i=1; i<lengthCS; i++){
            ctxCS.moveTo(EMPCARD_WIDTH*(i*2+1)/2+EMPCARD_PADDING*i, CANVAS_HEIGHT/2)
            ctxCS.lineTo(EMPCARD_WIDTH*(i*2+1)/2+EMPCARD_PADDING*i, CANVAS_HEIGHT)
        }
        ctxCS.stroke()
    }
}

