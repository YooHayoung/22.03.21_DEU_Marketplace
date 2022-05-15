import React from "react";

import './css/ItemInfo.scss'
import noImg from '../../../image/noImg.png'
import { Box, Button, Card, Chip, FormControl, InputLabel, MenuItem, Modal, Select, TextField, Typography } from "../../../../node_modules/@material-ui/core/index";
import { DateTimePicker, LocalizationProvider } from "../../../../node_modules/@mui/lab/index";
import { Stack } from "../../../../node_modules/@mui/material/index";
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns';
import { UseApi } from "../../../api/UseApi";
import { createDeal, updateDealInfo } from "../../../api/Api";


const style = {
   position: 'absolute',
   top: '50%',
   left: '50%',
   transform: 'translate(-50%, -50%)',
   width: '100%',
   bgcolor: 'background.paper',
   border: '2px solid #000',
   boxShadow: 24,
   p: 4,
 };

const ItemInfo = (props) => {
   const [open, setOpen] = React.useState(false);
   const [meetingPlace, setMeetingPlace] = React.useState(props.newChatRoomInfo==null?((props.roomInfo.dealInfo!=null?(Object.keys(props.roomInfo.dealInfo).length):0)!=0?props.roomInfo.dealInfo.meetingPlace:''):null);
   const [dateTime, setDateTime] = React.useState(props.newChatRoomInfo==null?((props.roomInfo.dealInfo!=null?(Object.keys(props.roomInfo.dealInfo).length):0)!=0?(new Date(Date.parse(props.roomInfo.dealInfo.appointmentDate.replace(' ', 'T')))):(new Date())):null);
   const [isUpdateFieldReadOnly, setIsUpdateFieldReadOnly] = React.useState(true);

   
   const handleUpdateFieldReadOnly = () => {
      setIsUpdateFieldReadOnly(true);
      props.onUpdateBtnClick((new Date(+dateTime+(3240 * 10000))).toISOString().replace("T", "_").replace(/\..*/, ''), meetingPlace);
   };
   const handleUpdateFieldReadAndWrite = () => {
      setIsUpdateFieldReadOnly(false);
   };

   const handleOpen = () => {
      setOpen(true);
  };
  const handleClose = () => {
      setOpen(false);
  };
   
   const renderDealState = (dealState) => {
      if (dealState === 'APPOINTMENT') return "예약중";
      else if (dealState === 'COMPLETE') return "거래완료";
      else return null;
   };

   const onMeetingPlaceHandleChange = (e) => {
      setMeetingPlace(e.target.value);
   };
   const dateTimeHandleChange = (newValue) => {
      setDateTime(newValue);
   };

   const onCompleteBtnClick = () => {
      if (window.confirm("거래가 완료되었습니까?")) {
         props.onCompleteBtnClick();
      }
   };
   const onCancelBtnClick = () => {
      if (window.confirm("예약을 취소하시겠습니까?")) {
         props.onCancelBtnClick();
         setMeetingPlace('');
         setDateTime('');
      }
   };
   const renderDateTimePicker = () => {
      return (
         <LocalizationProvider dateAdapter={AdapterDateFns}>
            {/* <Stack spacing={1}> */}
            <DateTimePicker
               disabled={props.roomInfo.itemInfo.dealState?isUpdateFieldReadOnly:false}
               label="거래 시각"
               value={dateTime}
               onChange={dateTimeHandleChange}
               renderInput={(params) => <TextField variant="standard" {...params} />}
            />
            {/* </Stack> */}
         </LocalizationProvider>
      );
   };
  

   const renderModal = (mTitle) => {
      return (
         <Modal
            open={open}
            onClose={handleClose}
            aria-labelledby="modal-title"
            aria-describedby="modal-description"
         >
            <Box sx={style}>
               <Typography id="modal-title" variant="h6" component="h2">
                  {mTitle}
               </Typography>
               <Typography id="modal-description" sx={{ mt: 2 }}>
                  <FormControl fullWidth style={{marginBottom: 20}}>
                     {renderDateTimePicker()}
                  </FormControl>
                  <FormControl id="div_place" fullWidth style={{marginBottom: 5}}>
                     <TextField disabled={props.roomInfo.itemInfo.dealState?isUpdateFieldReadOnly:false} label="거래 장소" variant="standard" value={meetingPlace} onChange={onMeetingPlaceHandleChange} />
                  </FormControl>
                  <div id="btn_search">
                     {isUpdateFieldReadOnly?(props.roomInfo.itemInfo.dealState?(props.roomInfo.itemInfo.dealState=="APPOINTMENT"?<Button onClick={onCancelBtnClick}>예약취소</Button>:null):null):null}
                     {isUpdateFieldReadOnly?(props.roomInfo.itemInfo.dealState?(props.roomInfo.itemInfo.dealState=="APPOINTMENT"?<Button onClick={onCompleteBtnClick}>거래완료</Button>:null):null):null}
                     {props.roomInfo.itemInfo.dealState?(props.roomInfo.itemInfo.dealState=="APPOINTMENT"?(isUpdateFieldReadOnly?<Button onClick={handleUpdateFieldReadAndWrite}>예약수정</Button>:<Button onClick={handleUpdateFieldReadOnly}>수정완료</Button>):null):null}
                     {props.roomInfo.itemInfo.dealState?null:<Button onClick={() => {
                        props.onBtnClick((new Date(+dateTime+(3240 * 10000))).toISOString().replace("T", "_").replace(/\..*/, ''), meetingPlace);
                     }}>저장</Button>}
                  </div>
               </Typography>
            </Box>
         </Modal>
      );
   };

   return (
      <>
      {props.newChatRoomInfo==null?(renderModal(props.roomInfo.itemInfo.dealState?'상태변경':'예약하기')):null}
      <Card className="div_itemInfo">
         <div className="itemImg">{props.newChatRoomInfo==null?(props.roomInfo.itemInfo.itemImg === null ? <img src={noImg} /> : <img src={props.roomInfo.itemInfo.itemImg} />):(props.newChatRoomInfo.itemInfo.itemImg === null ? <img src={noImg} /> : <img src={props.newChatRoomInfo.itemInfo.itemImg} />)}</div>
         <div className="title">{props.newChatRoomInfo==null?props.roomInfo.itemInfo.title:props.newChatRoomInfo.itemInfo.title}</div>
         <div className="price">{(props.newChatRoomInfo==null?props.roomInfo.itemInfo.price:props.newChatRoomInfo.itemInfo.price).toLocaleString()}원</div>
         {/* {props.roomInfo.itemInfo.dealState?<div className="dealState">{renderDealState(props.roomInfo.itemInfo.dealState)}</div>:null} */}
         {props.newChatRoomInfo==null?(props.roomInfo.itemInfo.dealState?<Chip className="dealState" label={renderDealState(props.roomInfo.itemInfo.dealState)} size="small" style={{fontSize: "0.7rem"}} color={props.roomInfo.dealInfo.dealTargetMemberInfo.memberId==props.roomInfo.requestedMemberInfo.memberId?(props.roomInfo.itemInfo.dealState=="APPOINTMENT"?"info":"success"):"default"} />:null):(props.newChatRoomInfo.itemInfo.dealState?<Chip className="dealState" label={renderDealState(props.newChatRoomInfo.itemInfo.dealState)} size="small" style={{fontSize: "0.7rem"}} color={props.newChatRoomInfo.itemInfo.dealState=="APPOINTMENT"?"default":"success"} />:null)}
         {props.newChatRoomInfo==null?(props.roomInfo.dealInfo?(props.roomInfo.itemSavedMemberInfo.memberId==props.myId&&props.roomInfo.itemInfo.dealState!=="COMPLETE"&&props.roomInfo.dealInfo.dealTargetMemberInfo.memberId==props.roomInfo.requestedMemberInfo.memberId?
            <Button className="btn_stateChange" 
               variant="contained" 
               size="small" 
               onClick={handleOpen}
               style={{marginTop: 4}}
            >
               {props.roomInfo.itemInfo.dealState?'상태변경':'예약하기'}
            </Button>
         :null):(props.roomInfo.itemSavedMemberInfo.memberId==props.myId?
            <Button className="btn_stateChange" 
               variant="contained" 
               size="small" 
               onClick={handleOpen}
               style={{marginTop: 4}}
            >
               {props.roomInfo.itemInfo.dealState?'상태변경':'예약하기'}
            </Button>
            :null)):null}
      </Card>
      </>
   );
};

export default ItemInfo;