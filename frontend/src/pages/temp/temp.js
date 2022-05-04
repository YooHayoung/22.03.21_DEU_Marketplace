import React, { useState } from "react";
import './InputItemInfoPage.scss';
import Dropdown from './Dropdown';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import WallpaperIcon from '@mui/icons-material/Wallpaper';
import { InputAdornment } from '@mui/material';
import Box from '@mui/material/Box';
import HeaderContainer from "../containers/HeaderContainer";
import BottomNav from "../components/nav/bottom/BottomNav";

const InputItemInfoPage = () => {
    const [selected, setSelected] = useState("카테고리");
    /*const [selectedd, setSelectedd] = useState("과목명");*/
    return (
        <>
        <HeaderContainer pageName={"물품 등록"} />
        {/* <div className="inputForm"> */}
          <div className='photo'>
            <div className='img'><Box
          sx={{
            '& > :not(style)': {
              m: 2,
            },
          }}>
          <WallpaperIcon sx= {{fontSize:140}}/></Box></div>
          <div className='ph'>
          <h2>사진등록</h2>
          </div>
          </div><hr/>
          <div className='title'>
              <div className='titletext'>
              <h3>제목</h3>
              </div>
              <div className='titletextbox'>
              <TextField size='small' id="outlined-basic" label="책제목입력" variant="outlined" />
              </div>
            </div>
            <hr/>
            <div className='category'>
    <div className='catext'>
    <h3>카테고리</h3></div>
    <div className='catdrop'>
    <Dropdown/>
    </div>
    </div>
    
    <hr/> 
            <div className='qwer'>
    <div className='haktext'>
      <h3>학과</h3></div>
      <div className='haktextfield'>
    <TextField
        required
        width='10%'
        size='small'
        id="outlined-required"
        label="Required"
        defaultValue="학과명"
      /></div>
       
         <div className='lectext'>
    <h3>과목명</h3></div>
    <div className='lectextfield'><TextField
        disabled
        width='10%'
        size='small'
        id="outlined-disabled"
        label="Disabled"
        defaultValue="과목명"
      /></div>
    <div className='qwerbtn'>
      <Button variant="contained">검색</Button>  
      </div> 
          </div><hr/>
          <div className='mon'>
            <div className='montext'>
            <h3> 희망금액 </h3></div>
              <div className='want'>
              <TextField InputProps= {{
                startAdornment : <InputAdornment position='end'>원</InputAdornment>
              }}
               id="cash" label="금액입력" variant="standard" /></div>
          </div>
          <hr/>
    <div className='ex'>
    <div className='extext'>
      <h3 className='test'>설명</h3>
    </div>
    <div className='extextfield'>
    <TextField    
      id="outlined-multiline-static"
      label="Multiline"
      multiline
      rows={4}
      defaultValue="내용 입력"
    
    /></div>
    
    <div className='exbtn'><Button variant="contained">등록</Button></div>
        {/* <BotBar /> */}
        </div>
        {/* </div> */}
        </>
        );
    };

export default InputItemInfoPage;