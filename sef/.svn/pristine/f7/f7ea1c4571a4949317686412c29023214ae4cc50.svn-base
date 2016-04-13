//! include /ui/sef/js/eventConfiguration/data.js

var DataUtil={
		
		getEventDetailByType:function(eventType){
			
			for(var i=0;i<eventConfigData.length;i++){
				
				if(eventConfigData[i].eventType===eventType){
					return eventConfigData[i];
				}
			}
			
		},
		
	    getSubscriberDetailByTypeByName:function(eventType,subscribeName){
			
	    	var eventDetail={};
	    	var subscribingModule={};
	    	
			for(var i=0;i<eventConfigData.length;i++){
				
				if(eventConfigData[i].eventType===eventType){
					eventDetail=eventConfigData[i];
					break;
				}
			}
			
			for(var i=0;i<eventDetail.SubscribingModules.length;i++){
				if(eventDetail.SubscribingModules[i].SubscribingModule==subscribeName){
					subscribingModule=eventDetail.SubscribingModules[i];
					break;
				}
			}
			
			return subscribingModule;
			
		},
		
		getNotificationTypebyType:function(eventType){
			
			var notificationType=[];
			
			var eventSubscritionDetail=this.getEventDetailByType(eventType);

			if (eventSubscritionDetail) {
				for(var i=0;i<eventSubscritionDetail.SubscribingModules.length;i++){
					var notifType=eventSubscritionDetail.SubscribingModules[i].NotificationType;

					if(notificationType.indexOf(notifType)==-1 && notifType!=='N/A'){
						notificationType.push(notifType);
					}
				}
			}

			return notificationType;
		},
		
		getNotificationDetailByNotifType:function(notifType,eventType){
			
			var notifDetailForThisType=[];
			var eventSubscritionDetail=this.getEventDetailByType(eventType);
			
			for(var i=0;i<eventSubscritionDetail.SubscribingModules.length;i++){
				
				if(eventSubscritionDetail.SubscribingModules[i].NotificationType===notifType){
					notifDetailForThisType.push(eventSubscritionDetail.SubscribingModules[i]);
				}
			}
			
			return notifDetailForThisType;
			
		}
		
		
		
		
	}