// import {Modal, Form, Select, Input, Button} from 'antd';

// let React = require('react');
// let $ = require('jquery');
// require('./../../lib/jquery.form.js');
// require('./../../css/createBannerDialog.scss');

// const FormItem = Form.Item;

// let DialogForm = React.createClass({
//   getInitialState() {
//     return {
//       createVideo: true,
//       createAd: false,
//       typeValue: "video",
//       deviceValue: "whole",
//       videoId: "",
//       description: "",
//       url: "",
//       formMethod: "post",
//       id: ""
//     };
//   },
//   componentDidMount() {
//     events.subscribe("resetSelect",(function(){
//       this.setState({
//         typeValue: 'video',
//         createVideo: true,
//         createAd: false,
//         deviceValue: "whole",
//         videoId: "",
//         description: "",
//         url: "",
//         formMethod: "post",
//         id: ""
//       });
//     }).bind(this));

//     events.subscribe("editBanner",(function(bannerInfo){
//       if(bannerInfo.kind === "video") {
//         bannerInfo.createVideo = true;
//         bannerInfo.createAd = false;
//       } else {
//         bannerInfo.createVideo = false;
//         bannerInfo.createAd = true;
//       }
//       this.setState({
//         typeValue: bannerInfo.kind,
//         createVideo: bannerInfo.createVideo,
//         createAd: bannerInfo.createAd,
//         deviceValue: bannerInfo.device_type,
//         videoId: bannerInfo.video_id,
//         description: bannerInfo.description,
//         url: bannerInfo.source_url,
//         formMethod: "put",
//         id: bannerInfo.id
//       });
//       $('#videoThumbnailImg').attr('src',bannerInfo.image);
//     }).bind(this));
//   },
//   componentWillUnmount: function(){
//     events.unsubscribe('resetSelect');
//     events.unsubscribe('editBanner');
//   },
//   typeSelection(value) {
//     if(value === "video") {
//       this.setState({createVideo: true, createAd: false, typeValue: 'video', url: ""});
//     } else {
//       this.setState({createVideo: false, createAd: true, typeValue: 'url', videoId: ""});
//     }
//   },
//   deviceSelection(value) {
//     if(value === "whole") {
//       this.setState({deviceValue: 'whole'});
//     } else if(value === "ios") {
//       this.setState({deviceValue: 'ios'});
//     } else {
//       this.setState({deviceValue: 'android'});
//     }
//   },
//   videoIdInputChange(e) {
//     this.setState({videoId: e.target.value.trim()});
//   },
//   descriptionInputChange(e) {
//     this.setState({description: e.target.value.trim()});
//   },
//   urlInputChange(e) {
//     this.setState({url: e.target.value.trim()});
//   },
//   handleInputChange(e) {
//     if(e.target.type=='file')
//     {
//       var fr = new FileReader();
//       fr.readAsDataURL(e.target.files[0]);
//       fr.onload = function(e) {
//          $('#videoThumbnailImg').attr('src',e.target.result);
//       };
//     }
//   },
//   fillVideoInfo() {
//     let _this = this;
//     ajaxEx.get({url:Const.API.videos + "/" + this.state.videoId,enabledLoadingMsg:true,enabledErrorMsg:true},
//     function(data){
//       _this.setState({description: data.data.attributes.title});
//        $('#videoThumbnailImg').attr('src',data.data.attributes.thumbnail);
//     });
//   },
//   render() {
//     return (
//       <Form horizontal className="dialogForm" id="createDialogForm" method={this.state.formMethod}>
//         <FormItem
//           label="类型选择">
//           <Select onSelect={this.typeSelection} value={this.state.typeValue}>
//            <Option value="video">视频</Option>
//            <Option value="url">推广链接</Option>
//          </Select>
//         </FormItem>
//         <Input type="hidden" name="banner[kind]" value={this.state.typeValue} />

//         <FormItem label="视频ID" className={this.state.createVideo ? "": "hidden"}>
//           <Input id="videoID" name="banner[video_id]" value={this.state.videoId} onChange={this.videoIdInputChange} />
//           <a href="javascript:void(0)" onClick={this.fillVideoInfo}>根据ID填充</a>
//         </FormItem>

//         <FormItem label="推广页面地址" className={this.state.createAd ? "": "hidden"}>
//           <Input name="banner[source_url]" id="AdUrl" value={this.state.url} onChange={this.urlInputChange} />
//         </FormItem>

//         <FormItem label="描述">
//           <Input name="banner[description]" type="textarea" value={this.state.description} onChange={this.descriptionInputChange} id="description" />
//         </FormItem>

//         <FormItem
//           label="缩略图：" labelCol={{ span: 3 }} wrapperCol={{ span: 14 }}>
//           <img id="videoThumbnailImg" />
//           <div>
//             <a className="file-upload">
//                选择文件
//                <Input type="file" id="bannerThumbnail" name='banner[image]' onChange={this.handleInputChange}/>
//             </a>
//           </div>
//         </FormItem>

//         <FormItem
//           label="设备选择">
//           <Select onSelect={this.deviceSelection} value={this.state.deviceValue}>
//             <Option value="whole">全部</Option>
//             <Option value="ios">IOS</Option>
//             <Option value="android">Android</Option>
//           </Select>
//         </FormItem>
//         <Input type="hidden" name="banner[device_type]" value={this.state.deviceValue} />
//       </Form>
//     );
//   }
// });

// const CreateBannerDialog = React.createClass({
//   getInitialState() {
//     return {
//       loading: false,
//       visible: false
//     };
//   },
//   componentDidMount() {
//     events.subscribe("showBannerDialog",(function(){
//       this.setState({
//         visible: true
//       });
//     }).bind(this));
//   },
//   componentWillUnmount: function(){
//     events.unsubscribe('showBannerDialog');
//   },
//   handleOk() {
//     ajaxEx.submit({url:Const.API.banners + "/" + this.refs.dialogForm.state.id ,formEle:$('#createDialogForm')[0],enabledLoadingMsg:true,enabledErrorMsg:true},function() {
//       events.publish("refreshTable");
//     });
//     this.resetDialog();
//   },
//   handleCancel() {
//     this.resetDialog();
//   },
//   resetDialog() {
//     $('#createDialogForm').resetForm();
//     $('#videoThumbnailImg').attr("src", "");
//     events.publish("resetSelect");
//     this.setState({
//       visible: false
//     });
//   },
//   render() {
//     return (
//         <Modal ref="modal"
//           visible={this.state.visible}
//           title="创建轮播图" onOk={this.handleOk} onCancel={this.handleCancel}
//           footer={[
//             <Button key="back" type="ghost" size="large" onClick={this.handleCancel}>返 回</Button>,
//             <Button key="submit" type="primary" size="large" loading={this.state.loading} onClick={this.handleOk}>
//               确定
//             </Button>
//           ]}>
//           <DialogForm ref="dialogForm" />
//         </Modal>
//     );
//   }
// });

// module.exports = CreateBannerDialog;
