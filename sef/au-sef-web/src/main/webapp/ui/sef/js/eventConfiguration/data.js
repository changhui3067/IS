var eventConfigData= [
    {
      "eventType":"IndividualContributorBecomesManager",
      "PublishingEvent": MSGS.SEF_EVENT_INDIVIDUAL_CONTRIBUTOR_TO_MANAGER,
      "PublishingEventDescription": MSGS.SEF_EVENT_INDIVIDUAL_CONTRIBUTOR_TO_MANAGER_DESC,
      "PublishingModule": MSGS.SEF_PUBLISHER_EC,
      "PublishingPrepackagedRule": "EVENT_IC2MGR",
      "PublishedExternally": "Yes",
      "SubscribingModules": [
        {
          "SubscribingModule": MSGS.SEF_MODULE_GM,
          "SubscribingImpactArea":MSGS.SEF_SUB_IMP_AREA_MGR_CHNG_GM,
          "SubscriberDescription":MSGS.SEF_SUB_DESC_HOME_PAGE_TILE_FIRST_TIME_MGR,
          "NotificationDescription":MSGS.SEF_SUB_DESC_ABSENCE_EMP_LONG_DIS_GM_REVIEW_GOAL,
          "NotificationType": MSGS.SEF_SUB_NOTIFICATION_T0DO_NOTIFICATION
        },
        {
          "SubscribingModule": MSGS.SEF_MODULE_LMS,
          "SubscribingImpactArea":MSGS.SEF_SUB_IMP_AREA_EMP_HIRE_LEARNING,
          "SubscriberDescription":MSGS.SEF_SUB_DESC_LEARNING_IND_CONT_TO_MGR,
          "NotificationDescription": MSGS.SEF_SUB_NA,
          "NotificationType": MSGS.SEF_SUB_NA
        }
      ]
    },
    {
      "eventType":"ManagerBecomesIndividualContributor",
      "PublishingEvent":MSGS.SEF_EVENT_MANAGER_TO_INDIVIDUAL_CONTRIBUTOR_DESC,
      "PublishingEventDescription":MSGS.SEF_EVENT_MANAGER_TO_INDIVIDUAL_CONTRIBUTOR_DESC,
      "PublishingModule":MSGS.SEF_PUBLISHER_EC,
      "PublishingPrepackagedRule": "EVENT_MGR2IC",
      "PublishedExternally": "Yes",
      "SubscribingModules": []
    },
    {
      "eventType":"FirstTimeManager",
      "PublishingEvent":MSGS.SEF_EVENT_FIRST_TIME_MANAGER,
      "PublishingEventDescription":MSGS.SEF_EVENT_FIRST_TIME_MANAGER_DESC,
      "PublishingModule":MSGS.SEF_PUBLISHER_EC,
      "PublishingPrepackagedRule": "EVENT_MGRFT",
      "PublishedExternally": "Yes",
      "SubscribingModules": [
         {
           "SubscribingModule": MSGS.SEF_MODULE_GM,
           "SubscribingImpactArea":MSGS.SEF_SUB_IMP_AREA_MGR_CHNG_GM,
           "SubscriberDescription":MSGS.SEF_SUB_DESC_HOME_PAGE_TILE_FIRST_TIME_MGR,
           "NotificationDescription":MSGS.SEF_SUB_DESC_ABSENCE_EMP_LONG_DIS_GM_REVIEW_GOAL,
           "NotificationType": MSGS.SEF_SUB_NOTIFICATION_T0DO_NOTIFICATION
         }
      ]
    },
    {
      "eventType":"LocationChange",
      "PublishingEvent":MSGS.SEF_EVENT_EMPLOYEE_LOCATION,
      "PublishingEventDescription":MSGS.SEF_EVENT_EMPLOYEE_LOCATION_DESC,
      "PublishingModule":MSGS.SEF_PUBLISHER_EC,
      "PublishingPrepackagedRule": "EVENT_LOCNCHG",
      "PublishedExternally": "Yes",
      "SubscribingModules": []
    },
    {
      "eventType":"DepartmentChange",
      "PublishingEvent":MSGS.SEF_EVENT_EMPLOYEE_DEPARTMENT,
      "PublishingEventDescription":MSGS.SEF_EVENT_EMPLOYEE_DEPARTMENT_DESC,
      "PublishingModule":MSGS.SEF_PUBLISHER_EC,
      "PublishingPrepackagedRule": "EVENT_DEPTCHG",
      "PublishedExternally": "Yes",
      "SubscribingModules": []
    },
    {
      "eventType":"DivisionChange",
      "PublishingEvent":MSGS.SEF_EVENT_EMPLOYEE_DIVISION,
      "PublishingEventDescription":MSGS.SEF_EVENT_EMPLOYEE_DIVISION_DESC,
      "PublishingModule":MSGS.SEF_PUBLISHER_EC,
      "PublishingPrepackagedRule": "EVENT_DIVNCHG",
      "PublishedExternally": "Yes",
      "SubscribingModules": []
    },
    {
      "eventType":"JobTitleChange",
      "PublishingEvent":MSGS.SEF_EVENT_JOB_TITLE,
      "PublishingEventDescription":MSGS.SEF_EVENT_JOB_TITLE_DESC,
      "PublishingModule":MSGS.SEF_PUBLISHER_EC,
      "PublishingPrepackagedRule": "EVENT_TITLCHG",
      "PublishedExternally": "Yes",
      "SubscribingModules": []
    },
    {
        "eventType":"BusinessUnitChange",
        "PublishingEvent":MSGS.SEF_EVENT_BUSINESSUNITCHANGE,
        "PublishingEventDescription":MSGS.SEF_EVENT_BUSINESSUNITCHANGE_DESC,
        "PublishingModule":MSGS.SEF_PUBLISHER_EC,
        "PublishingPrepackagedRule": "EVENT_BUCHG",
        "PublishedExternally": "Yes",
        "SubscribingModules": []
    },
    {
        "eventType":"JobClassificationChange",
        "PublishingEvent":MSGS.SEF_EVENT_JOBCLASSIFICATIONCHANGE,
        "PublishingEventDescription":MSGS.SEF_EVENT_JOBCLASSIFICATIONCHANGE_DESC,
        "PublishingModule":MSGS.SEF_PUBLISHER_EC,
        "PublishingPrepackagedRule": "EVENT_JBCLSCHG",
        "PublishedExternally": "Yes",
        "SubscribingModules": []
    },
    {
        "eventType":"ReHire",
        "PublishingEvent":MSGS.SEF_EVENT_REHIRE,
        "PublishingEventDescription":MSGS.SEF_EVENT_REHIRE_DESC,
        "PublishingModule":MSGS.SEF_PUBLISHER_EC,
        "PublishingPrepackagedRule": "EVENT_REH",
        "PublishedExternally": "Yes",
        "SubscribingModules": []
    },
    {
      "eventType":"ManagerChange",
      "PublishingEvent":MSGS.SEF_EVENT_CHANGE_IN_MANAGER_DESC,
      "PublishingEventDescription":MSGS.SEF_EVENT_JOB_GRADE_DESC,
      "PublishingModule":MSGS.SEF_PUBLISHER_EC,
      "PublishingPrepackagedRule": "EVENT_MNGRCHG",
      "PublishedExternally": "Yes",
      "SubscribingModules": [
        {
          "SubscribingModule":MSGS.SEF_MODULE_LMS,
          "SubscribingImpactArea":MSGS.SEF_SUB_IMP_AREA_MGR_CHNG_LEARNING,
          "SubscriberDescription":MSGS.SEF_SUB_DESC_MGR_CHNG_LEARNING,
          "NotificationDescription": MSGS.SEF_SUB_NA,
          "NotificationType": MSGS.SEF_SUB_NA
        },
        {
          "SubscribingModule": MSGS.SEF_MODULE_SM,
          "SubscribingImpactArea":MSGS.SEF_SUB_IMP_AREA_MGR_CHNG_GM,
          "SubscriberDescription":MSGS.SEF_SUB_DESC_MGR_CHNG_SM,
          "NotificationDescription": MSGS.SEF_SUB_IMP_AREA_SM_DESC,
          "NotificationType": MSGS.SEF_SUB_NOTIFICATION_T0DO_NOTIFICATION
        },
        {
          "SubscribingModule": MSGS.SEF_MODULE_CALIBERATION,
          "SubscribingImpactArea":MSGS.SEF_SUB_IMP_AREA_MGR_CHNG_GM,
          "SubscriberDescription":MSGS.SEF_SUB_DESC_MGR_CHNG_CALIBRATION,
          "NotificationDescription": MSGS.SEF_SUB_IMP_AREA_CALIBERATION_NOTIFICATION_DESC,
          "NotificationType": MSGS.SEF_SUB_NOTIFICATION_T0DO_NOTIFICATION
        },
        {
          "SubscribingModule": MSGS.SEF_MODULE_GM,
          "SubscribingImpactArea":MSGS.SEF_SUB_IMP_AREA_MGR_CHNG_GM,
          "SubscriberDescription":MSGS.SEF_SUB_DESC_HOME_PAGE_TILE_FIRST_TIME_MGR,
          "NotificationDescription":MSGS.SEF_SUB_DESC_ABSENCE_EMP_LONG_DIS_GM_REVIEW_GOAL,
          "NotificationType": MSGS.SEF_SUB_NOTIFICATION_T0DO_NOTIFICATION
        }
      ]
    },
    {
      "eventType":"Absence",
      "PublishingEvent":MSGS.SEF_EVENT_EMPLOYEE_LEAVE_ABSENCE,
      "PublishingEventDescription":MSGS.SEF_EVENT_EMPLOYEE_LEAVE_ABSENCE_DESC,
      "PublishingModule":MSGS.SEF_PUBLISHER_EC,
      "PublishingPrepackagedRule": "EVENT_LOASTA",
      "PublishedExternally": "Yes",
      "SubscribingModules": [
        {
          "SubscribingModule": MSGS.SEF_MODULE_GM,
          "SubscribingImpactArea":MSGS.SEF_SUB_IMP_AREA_ABSENCE_EMP_LONG_DIS_GM,
          "SubscriberDescription":MSGS.SEF_SUB_DESC_ABSENCE_EMP_LEAVE_GM,
          "NotificationDescription":MSGS.SEF_SUB_DESC_ABSENCE_EMP_LONG_DIS_GM,
          "NotificationType": MSGS.SEF_SUB_NOTIFICATION_T0DO_NOTIFICATION
        },
        {
          "SubscribingModule": MSGS.SEF_MODULE_PMTFT,
          "SubscribingImpactArea":MSGS.SEF_SUB_IMP_AREA_ABSENCE_EMP_LONG_DIS_TALENT,
          "SubscriberDescription":MSGS.SEF_SUB_DESC_ABSENCE_EMP_LEAVE_TALENT,
          "NotificationDescription": MSGS.SEF_SUB_IMP_AREA_PMTFT_NOTIFICATION_DESC,
          "NotificationType": MSGS.SEF_SUB_NOTIFICATION_EMAIL_NOTIFICATION
        },
        {
          "SubscribingModule": MSGS.SEF_MODULE_COMP,
          "SubscribingImpactArea":MSGS.SEF_SUB_IMP_AREA_ABSENCE_EMP_LONG_DIS_TALENT,
          "SubscriberDescription":MSGS.SEF_SUB_DESC_ABSENCE_EMP_LEAVE_TALENT,
          "NotificationDescription": MSGS.SEF_SUB_IMP_AREA_PMTFT_NOTIFICATION_DESC,
          "NotificationType": MSGS.SEF_SUB_NOTIFICATION_EMAIL_NOTIFICATION
        },
        {
          "SubscribingModule": MSGS.SEF_MODULE_VP,
          "SubscribingImpactArea":MSGS.SEF_SUB_IMP_AREA_ABSENCE_EMP_LONG_DIS_TALENT,
          "SubscriberDescription":MSGS.SEF_SUB_DESC_ABSENCE_EMP_LEAVE_TALENT,
          "NotificationDescription": MSGS.SEF_SUB_IMP_AREA_PMTFT_NOTIFICATION_DESC,
          "NotificationType": MSGS.SEF_SUB_NOTIFICATION_EMAIL_NOTIFICATION
        },  
        {
          "SubscribingModule": MSGS.SEF_MODULE_JAM,
          "SubscribingImpactArea":MSGS.SEF_SUB_IMP_AREA_ABSENCE_EMP_LEAVE_JAM,
          "SubscriberDescription": MSGS.SEF_SUB_IMP_AREA_JAM_SUB_DESC,
          "NotificationDescription": MSGS.SEF_SUB_NA,
          "NotificationType": MSGS.SEF_SUB_NA
        }
      ]
    },
    {
        "eventType":"ShortTermDisability",
        "PublishingEvent":MSGS.SEF_EVENT_EMPLOYEE_SHORT_TERM_DISABILITY,
        "PublishingEventDescription":MSGS.SEF_EVENT_EMPLOYEE_SHORT_TERM_DISABILITY_DESC,
        "PublishingModule":MSGS.SEF_PUBLISHER_EC,
        "PublishingPrepackagedRule": "EVENT_STDLSTA",
        "PublishedExternally": "Yes",
        "SubscribingModules": []
     },
     {
          "eventType":"LongTermDisability",
          "PublishingEvent":MSGS.SEF_EVENT_EMPLOYEE_LONG_TERM_DISABILITY,
          "PublishingEventDescription":MSGS.SEF_EVENT_EMPLOYEE_LONG_TERM_DISABILITY_DESC,
          "PublishingModule":MSGS.SEF_PUBLISHER_EC,
          "PublishingPrepackagedRule": "EVENT_LTDLSTA",
          "PublishedExternally": "Yes",
          "SubscribingModules": []
     },
     {
      "eventType":"Hire",
      "PublishingEvent":MSGS.SEF_EVENT_EMPLOYEE_HIRE,
      "PublishingEventDescription":MSGS.SEF_EVENT_EMPLOYEE_HIRE_DESC,
      "PublishingModule":MSGS.SEF_PUBLISHER_EC,
      "PublishingPrepackagedRule": "EVENT_HIRE",
      "PublishedExternally": "Yes",
      "SubscribingModules": [
        {
          "SubscribingModule":MSGS.SEF_MODULE_LMS,
          "SubscribingImpactArea":MSGS.SEF_SUB_IMP_AREA_EMP_HIRE_LEARNING,
          "SubscriberDescription": MSGS.SEF_SUB_IMP_AREA_LMS_SUB_DESC,
          "NotificationDescription": MSGS.SEF_SUB_NA,
          "NotificationType": MSGS.SEF_SUB_NA
        }
      ]
    },
    {
      "eventType":"Termination",
      "PublishingEvent":MSGS.SEF_EVENT_EMPLOYEE_TERMINATION,
      "PublishingEventDescription":MSGS.SEF_EVENT_EMPLOYEE_TERMINATION_DESC,
      "PublishingModule":MSGS.SEF_PUBLISHER_EC,
      "PublishingPrepackagedRule": "EVENT_TERM",
      "PublishedExternally": "Yes",
      "SubscribingModules": []
    },
    {
      "eventType":"JobRequisition.Updated",
      "PublishingEvent":MSGS.SEF_EVENT_UPDATE_JOB_REQ,
      "PublishingEventDescription":MSGS.SEF_EVENT_UPDATE_JOB_REQ_DESC,
      "PublishingModule":MSGS.SEF_PUBLISHER_RCM,
      "PublishingPrepackagedRule": MSGS.SEF_SUB_NA,
      "PublishedExternally": "No",
      "SubscribingModules": [
        {
          "SubscribingModule":MSGS.SEF_MODULE_OB,
          "SubscribingImpactArea":MSGS.SEF_SUB_IMP_AREA_JOB_REQ_UPDATE_ONB,
          "SubscriberDescription":MSGS.SEF_SUB_IMP_AREA_JOB_REQ_UPDATE_ONB_DESC,
          "NotificationDescription": MSGS.SEF_SUB_NA,
          "NotificationType": MSGS.SEF_SUB_NA
        }
      ]
    },
    {
      "eventType":"JobApplication.Updated",
      "PublishingEvent":MSGS.SEF_EVENT_UPDATE_JOB_APP,
      "PublishingEventDescription":MSGS.SEF_EVENT_UPDATE_JOB_APP_DESC,
      "PublishingModule":MSGS.SEF_PUBLISHER_RCM,
      "PublishingPrepackagedRule": MSGS.SEF_SUB_NA,
      "PublishedExternally": "No",
      "SubscribingModules": [
        {
          "SubscribingModule":MSGS.SEF_MODULE_OB,
          "SubscribingImpactArea":MSGS.SEF_SUB_IMP_AREA_JOB_APP_UPDATE_ONB,
          "SubscriberDescription": MSGS.SEF_SUB_IMP_AREA_JOB_APP_UPDATE_ONB_DESC,
          "NotificationDescription": MSGS.SEF_SUB_NA,
          "NotificationType": MSGS.SEF_SUB_NA
        }
      ]
    },
    {
      "eventType":"JobOffer.Updated",
      "PublishingEvent":MSGS.SEF_EVENT_APPROVAL_OFFER_DEATIL,
      "PublishingEventDescription":MSGS.SEF_EVENT_APPROVAL_OFFER_DEATIL_DESC,
      "PublishingModule":MSGS.SEF_PUBLISHER_RCM,
      "PublishingPrepackagedRule": MSGS.SEF_SUB_NA,
      "PublishedExternally": "No",
      "SubscribingModules": [
        {
          "SubscribingModule":MSGS.SEF_MODULE_OB,
          "SubscribingImpactArea":MSGS.SEF_SUB_IMP_AREA_JOB_OFFER_UPDATE_ONB,
          "SubscriberDescription": MSGS.SEF_SUB_IMP_AREA_JOB_OFFER_UPDATE_ONB_DESC,
          "NotificationDescription": MSGS.SEF_SUB_NA,
          "NotificationType": MSGS.SEF_SUB_NA
        }
      ]
    }
  ];