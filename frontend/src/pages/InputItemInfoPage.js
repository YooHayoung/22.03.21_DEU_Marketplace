import React, { useState } from "react";
import './InputItemInfoPage.scss';
import HeaderContainer from "../containers/HeaderContainer";

import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import Container from '@mui/material/Container';
import Radio from '@mui/material/Radio';
import RadioGroup from '@mui/material/RadioGroup';
import FormControlLabel from '@mui/material/FormControlLabel';
import FormControl from '@mui/material/FormControl';
import FormLabel from '@mui/material/FormLabel';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import Select from '@mui/material/Select';
import Box from '@mui/material/Box';
import { FormHelperText, InputAdornment, List, ListItem, ListItemText, OutlinedInput, Typography } from '../../node_modules/@material-ui/core/index';
import { UseApi } from '../api/UseApi';
import { getItemCategory, getLectures, getPostCategory, saveItem, saveItemImgs } from '../api/Api';
import { Link } from '../../node_modules/react-router-dom/index';
import Modal from '@mui/material/Modal';
import { ListItemButton } from "../../node_modules/@mui/material/index";
import { FixedSizeList } from 'react-window';
import { Delete } from "../../node_modules/@material-ui/icons/index";
import AddPhotoAlternateIcon from '@mui/icons-material/AddPhotoAlternate';

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

const InputItemInfoPage = ({token, setToken}) => {
    const [itemId, setItemId] = React.useState(0);
    const [imgs, setImgs] = React.useState([]);
    const [files, setFiles] = React.useState([]);
    const [classification, setClassification] = React.useState('sell');
    const [selectCategory, setSelectCategory] = React.useState('');
    const [itemCategory, setItemCategory] = React.useState([]);
    const [postCategory, setPostCategory] = React.useState([]);
    const [lecture, setLecture] = React.useState({
        lectureId: '',
        lectureName: '',
        professorName: ''
    });
    const [bookState, setBookState] = React.useState({
        writeState: '',
        surfaceState: '',
        origPrice: ''
    });
    const [price, setPrice] = React.useState('');
    const [title, setTitle] = useState('');
    // const [selected, setSelected] = useState("카테고리");
    const [description, setDescription] = useState('');
    const [contents, setContents] = useState('');

    const [open, setOpen] = React.useState(false);
    const handleOpen = () => {
        setOpen(true);
    };
    const handleClose = () => {
        setOpen(false);
    };
    const [modalLectureName, setModalLectureName] = useState('');
    const [modalProfessorName, setModalProfessorName] = useState('');
    const onModalLectureNameHandleChange = (e) => {
        setModalLectureName(e.target.value);
    };
    const onModalProfessorNameHandleChange = (e) => {
        setModalProfessorName(e.target.value);
    };
    const [lectureList, setLectureList] = useState([]);
    const [lecturePage, setLecturePage] = useState(0);
    const [isLastLecturePage, setIsLastLecturePage] = useState(false);
    const [isFirstLecturePage, setIsFirstLecturePage] = useState(false);


    const addImg = (e) => {
        const nowSelectImgList = e.target.files;
        console.log(nowSelectImgList);
        setFiles(nowSelectImgList);
        const nowImgUrlList = [...imgs];
        for (let i = 0; i<nowSelectImgList.length; i++) {
            const nowImgUrl = URL.createObjectURL(nowSelectImgList[i]);
            nowImgUrlList.push(nowImgUrl);
        }
        setImgs(nowImgUrlList);
    };

    // X버튼 클릭 시 이미지 삭제
    const handleDeleteImage = (id) => {
        setImgs(imgs.filter((_, index) => index !== id));
    };

    const imgPlusButton = () => {
        return (
            <>
                <div className="div_img" key={0}>
                    <label
                    htmlFor="input_file"
                    className="lb_inputFile"
                    onChange={addImg}
                    >
                        <AddPhotoAlternateIcon className="icon_img" fontSize='inherit' />
                        <div className="txt_plus">이미지 등록({imgs.length}/10)</div>
                        <input 
                            type={"file"}
                            multiple="muliple"
                            id="input_file"
                            style={{display: 'none'}}
                            accept=".jpg,.jpeg,.png"
                        />
                    </label>
                </div>
            </>
        );
    };

    const renderImgs = () => {
        return (
            <>
            <div>
                <div className="div_imgList">
                    {imgs.length>=10?null:(imgPlusButton())}
                    {imgs.map((image, id) => (
                        <div className="div_img" key={id+1}>
                            <img src={image} alt={`${image}-${id+1}`} onClick={() => handleDeleteImage(id)}/>
                            <div className="div_imgSeq" onClick={() => handleDeleteImage(id)}>{id+1}</div>
                        </div>
                    ))}
                    {/* {imgs.length>=10?null:(imgPlusButton())} */}
                </div>
            </div>
            </>
        );
    };

    
    const setClassHandleChange = (event) => {
        setClassification(event.target.value);
    };
    const selectCategoryHandleChange = (event) => {
        setSelectCategory(event.target.value);
    };

    const afterGetItemCategory = (res) => {
        setItemCategory(res.data.body.result);
    };
    const afterGetPostCategory = (res) => {
        setPostCategory(res.data.body.result);
    };
    const onTitledHandleChange = (e) => {
        setTitle(e.target.value);
    };
    const onPriceHandleChange = (event) => {
        setPrice(event.target.value);
    };
    const onDescriptionHandleChange = (e) => {
        setDescription(e.target.value);
    };
    const onContentsHandleChange = (e) => {
        setContents(e.target.value);
    };
    const onWriteStateHandleChange = (e) => {
        setBookState((prevState) => {
            return {
                ...prevState,
                writeState: e.target.value
            }
        });
    };
    const onSurfaceStateHandleChange = (e) => {
        setBookState((prevState) => {
            return {
                ...prevState,
                surfaceState: e.target.value
            }
        });
    };
    const onOrigPriceHandleChange = (e) => {
        setBookState((prevState) => {
            return {
                ...prevState,
                origPrice: e.target.value
            }
        });
    };

    const afterGetLectures = (res) => {
        setLectureList(res.data.body.result.content);
        if (!res.data.body.result.last) {
            setIsLastLecturePage(false);
        } else {
            setIsLastLecturePage(true);
        }
        if (res.data.body.result.first) {
            setIsFirstLecturePage(true);
        } else {
            setIsFirstLecturePage(false);
        }
        setLecturePage(res.data.body.result.pageable.pageNumber);
        console.log(lecturePage);
        console.log(res.data.body);
    };

    const afterItemSaved = (res) => {
        const itemId = res.data.body.result;
        const formData = new FormData();
        formData.append("itemId", itemId);
        Array.from(files).forEach(file => formData.append("file", file));
        UseApi(saveItemImgs, token, setToken, afterItemImgSaved, formData)
    };

    const afterItemImgSaved = (res) => {
        console.log(res);
        window.location.href=`/item/${res.data.body.result}`;
    };

    // console.log(classification);
    //         console.log(imgs);
    //         console.log(selectCategory);
    //         console.log(lecture);
    //         console.log(bookState);
    //         console.log(price);
    //         console.log(description);
    const save = () => {
        let saveDto = {};
        if (classification==="sell") {
            saveDto = {
                classification: classification.toUpperCase(),
                title: title,
                itemCategoryInfo: {
                    categoryId: selectCategory.categoryId,
                    categoryName: selectCategory.categoryName
                },
                lectureInfo: {
                    lectureId: lecture.lectureId,
                    lectureName: lecture.lectureName,
                    professorName: lecture.professorName
                },
                bookStateInfo: {
                    writeState: bookState.writeState,
                    surfaceState: bookState.surfaceState,
                    regularPrice: bookState.origPrice
                },
                price: price,
                description: description
            };
            (async () => {
                UseApi(saveItem, token, setToken, afterItemSaved, saveDto);
            })();
        } else if (classification==="buy") {
            saveDto = {
                classification: classification.toUpperCase(),
                title: title,
                itemCategoryInfo: {
                    itemCategoryId: selectCategory.categoryId,
                    itemCategoryName: selectCategory.categoryName
                },
                price: price,
                description: description
            };
        } else if (classification==="board") {
            console.log("게시물 전송");
        } else {
            console.log("내용 작성 바람");
        }
    }

    const getLectureList = (pageNum) => {
        (async () => {
            UseApi(getLectures, token, setToken, afterGetLectures, {
                lectureName: modalLectureName,
                professorName: modalProfessorName,
                page: pageNum,
            });
        })();
    };
    // const getLectureListNext = () => {
    //     (async () => {
    //         UseApi(getLectures, token, setToken, afterGetLecturesNext, {
    //             lectureName: modalLectureName,
    //             professorName: modalProfessorName,
    //             page: lecturePage,
    //         });
    //     })();
    // };
    // const getLectureListBack = () => {
    //     (async () => {
    //         UseApi(getLectures, token, setToken, afterGetLecturesBack, {
    //             lectureName: modalLectureName,
    //             professorName: modalProfessorName,
    //             page: lecturePage-1,
    //         });
    //     })();
    // };

    const onSearchLectureClick = () => {
        getLectureList(0);
    };

    const onNextButtonClick = () => {
        getLectureList(lecturePage+1);
    };

    const onBackButtonClick = () => {
        getLectureList(lecturePage-1);
    };

    const renderItemCategory = () => {
        if (itemCategory.length != 0) {
            return (itemCategory.map((category,idx)=> {
                return <MenuItem key={"mi"+idx} value={category}>{category.categoryName}</MenuItem>
            }));
        }
    };

    const renderPostCategory = () => {
        if (postCategory.length != 0) {
            return (postCategory.map((category,idx)=> {
                return <MenuItem key={"mi"+idx} value={category}>{category.categoryName}</MenuItem>
            }));
        }
    };

    const selectLecture = (index) => {
        console.log(lectureList[index]);
        setLecture({
            lectureId: lectureList[index].lectureId,
            lectureName: lectureList[index].lectureName,
            professorName: lectureList[index].professorName,
        });
        handleClose();
    }

    function renderRow(props) {
        const { index, style } = props;
      
        return (
          <ListItem style={style} key={index} component="div" disablePadding>
            <ListItemButton onClick={() => selectLecture(index)}>
              <ListItemText id="listItem" primary={`${lectureList[index].lectureName} - ${lectureList[index].professorName}`} />
            </ListItemButton>
          </ListItem>
        );
    }

    const renderPageControlButton = () => {
        return (
            <div className="div_backAndNext">
            {isFirstLecturePage?null:(<Button id="btn_back" onClick={onBackButtonClick}>이전</Button>)}
            {isLastLecturePage?null:(<Button id="btn_next" onClick={onNextButtonClick}>다음</Button>)}
            </div>
        );
    };

    const renderModal = () => {
        return (
            <Modal
                    open={open}
                    onClose={handleClose}
                    aria-labelledby="modal-modal-title"
                    aria-describedby="modal-modal-description"
                >
                    <Box sx={style}>
                    <Typography id="modal-modal-title" variant="h6" component="h2">
                        강의 검색
                    </Typography>
                    <Typography id="modal-modal-description" sx={{ mt: 2 }}>
                        <FormControl id="div_lec" fullWidth>
                            <TextField label="강의명" className='search_lectureName' id="search_lectureName" onChange={onModalLectureNameHandleChange}/>
                        </FormControl>
                        <FormControl id="div_prof" fullWidth>
                            <TextField label="교수명" className='search_professorName' id="search_professorName" onChange={onModalProfessorNameHandleChange}/>
                        </FormControl>
                        <div id="btn_search">
                        <Button onClick={onSearchLectureClick}>검색</Button>
                        </div>
                        <Box
                        sx={{ width: '100%', height: 330, bgcolor: 'background.paper' }}
                        >
                        <FixedSizeList
                            height={300}
                            width={'100%'}
                            itemSize={40}
                            itemCount={lectureList.length}
                            overscanCount={5}
                        >
                            {renderRow}
                        </FixedSizeList>
                        </Box>
                    </Typography>
                    {lectureList.length!==0?renderPageControlButton():null}
                    </Box>
                </Modal>
        );
    };

    const renderLectureInfoFields = () => {
        return (
            <>
            <div className="div_lectureInfo">
                {renderModal()}
                <FormControl fullWidth onClick={handleOpen}>
                    <TextField disabled label="강의명" className='txt_lectureName' id="txt_lectureName" defaultValue={lecture.lectureName} />
                </FormControl>
                <FormControl fullWidth onClick={handleOpen}>
                    <TextField disabled label="교수명" className='txt_professorName' id="txt_professorName" defaultValue={lecture.professorName}/>
                </FormControl>
            </div>
            </>
        );
    };
    const renderBookStateInfoFields = () => {
        return (
        <>
            <Box className="div_bookStateInfo" fullWidth sx={{
                    '& > :not(style)': { mt: 0.6, mb: 1}
            }}>
                <FormControl fullWidth>
                <InputLabel id="wirteState_label">필기상태</InputLabel>
                <Select labelId="wirteState_label" id="txt_writeState" value={bookState.writeState} label="writeState" onChange={onWriteStateHandleChange}>
                    <MenuItem key={"mi"+1} value={"좋음"}>좋음</MenuItem>
                    <MenuItem key={"mi"+2} value={"보통"}>보통</MenuItem>
                    <MenuItem key={"mi"+3} value={"나쁨"}>나쁨</MenuItem>
                </Select>
                </FormControl>
                <FormControl fullWidth>
                <InputLabel id="surfaceState_label">외관상태</InputLabel>
                <Select labelId="surfaceState_label" id="txt_writeState" value={bookState.surfaceState} label="surfaceState" onChange={onSurfaceStateHandleChange}>
                    <MenuItem key={"mi"+4} value={"좋음"}>좋음</MenuItem>
                    <MenuItem key={"mi"+5} value={"보통"}>보통</MenuItem>
                    <MenuItem key={"mi"+6} value={"나쁨"}>나쁨</MenuItem>
                </Select>
                </FormControl>
                <FormControl fullWidth>
                <TextField  label="정가" className='txt_origPrice' id="txt_origPrice" onChange={onOrigPriceHandleChange} InputProps={{endAdornment:(<InputAdornment position="end">원</InputAdornment>)}} />
                </FormControl>
            </Box>
        </>
        );
    };
    const renderLectureAndBookStateInfoFields = () => {
        return (
            <>
            <div className="div_lectureInfo" >
                {renderModal()}
                <FormControl fullWidth onClick={handleOpen}>
                    <TextField 
                        label="강의명" 
                        className='txt_lectureName' 
                        id="txt_lectureName" 
                        value={lecture.lectureName} 
                        InputProps={{
                            readOnly: true,
                        }} InputLabelProps={{
                            shrink: true,
                        }} sx={{
                            '& > :not(style)': { mb: 1}
                    }}/>
                </FormControl>
                <FormControl fullWidth onClick={handleOpen}>
                    <TextField 
                        label="교수명" 
                        className='txt_professorName' 
                        id="txt_professorName" 
                        value={lecture.professorName} 
                        InputProps={{
                            readOnly: true,
                        }} 
                        InputLabelProps={{
                            shrink: true,
                    }}/>
                </FormControl>
            </div>
            <Box className="div_bookStateInfo" 
                fullWidth 
                sx={{
                    '& > :not(style)': { mt: 0.6, mb: 1}
                }}>
                <FormControl fullWidth>
                <InputLabel id="wirteState_label">필기상태</InputLabel>
                <Select labelId="wirteState_label" id="txt_writeState" value={bookState.writeState} label="writeState" onChange={onWriteStateHandleChange}>
                    <MenuItem key={"mi"+1} value={"좋음"}>좋음</MenuItem>
                    <MenuItem key={"mi"+2} value={"보통"}>보통</MenuItem>
                    <MenuItem key={"mi"+3} value={"나쁨"}>나쁨</MenuItem>
                </Select>
                {/* <TextField  label="필기상태" className='txt_writeState' id="txt_writeState" onChange={onWriteStateHandleChange}/> */}
                </FormControl>
                <FormControl fullWidth>
                <InputLabel id="surfaceState_label">외관상태</InputLabel>
                <Select labelId="surfaceState_label" id="txt_writeState" value={bookState.surfaceState} label="surfaceState" onChange={onSurfaceStateHandleChange}>
                    <MenuItem key={"mi"+4} value={"좋음"}>좋음</MenuItem>
                    <MenuItem key={"mi"+5} value={"보통"}>보통</MenuItem>
                    <MenuItem key={"mi"+6} value={"나쁨"}>나쁨</MenuItem>
                </Select>
                {/* <TextField  label="책상태" className='txt_surfaceState' id="txt_surfaceState" onChange={onSurfaceStateHandleChange}/> */}
                </FormControl>
                <FormControl fullWidth>
                {/* <OutlinedInput id="label_origPrice" onChange={onOrigPriceHandleChange} endAdornment={<InputAdornment position="end">원</InputAdornment>} />
                <FormHelperText>정가</FormHelperText> */}
                <TextField  label="정가" className='txt_origPrice' id="txt_origPrice" onChange={onOrigPriceHandleChange} InputProps={{endAdornment:(<InputAdornment position="end">원</InputAdornment>)}} />
                </FormControl>
            </Box>
            </>
        );
    }

    const renderItemInfoFields = () => {
        return (
        <>
        <FormControl>
                {/* <FormLabel id="trade_name" className='trade_name'>구분</FormLabel> */}
                <RadioGroup aria-labelledby="trade_name" name="trade" row defaultValue="sell" onChange={setClassHandleChange}>
                    <FormControlLabel value="sell" control={<Radio />} label="팝니다" />
                    <FormControlLabel value="buy" control={<Radio />} label="삽니다" />
                    <FormControlLabel value="board" control={<Radio />} label="게시판" />
                </RadioGroup>
            </FormControl>
            {renderImgs()}
            <FormControl fullWidth>
            <TextField label="제목" className='txt_title' id="txt_title" onChange={onTitledHandleChange}/>
            </FormControl>

            <FormControl fullWidth >
                <InputLabel id="category_label">카테고리</InputLabel>
                <Select labelId="category_label" id="category_select" value={selectCategory} label="category" onChange={selectCategoryHandleChange}>
                    {renderItemCategory()}
                </Select>
            </FormControl>

            {selectCategory.categoryName==="대학 교재"?(renderLectureAndBookStateInfoFields()):(selectCategory.categoryName==="강의 관련 물품"?renderLectureInfoFields():(selectCategory.categoryName==="서적"?renderBookStateInfoFields():null))}
            <FormControl fullWidth>
                <TextField label="희망가격" id="txt_price" onChange={onPriceHandleChange} InputProps={{endAdornment:(<InputAdornment position="end">원</InputAdornment>)}} />
                {/* <FormHelperText>희망가격</FormHelperText> */}
            </FormControl>
            <FormControl fullWidth>
            <TextField multiline label="설명" className='txt_description' id="txt_description" onChange={onDescriptionHandleChange} rows={10}/>
            </FormControl>
        </>
        );
    };

    const renderBuyItemInfoFields = () => {
        return (
        <>
        <FormControl>
                {/* <FormLabel id="trade_name" className='trade_name'>구분</FormLabel> */}
                <RadioGroup aria-labelledby="trade_name" name="trade" row defaultValue="sell" onChange={setClassHandleChange}>
                    <FormControlLabel value="sell" control={<Radio />} label="팝니다" />
                    <FormControlLabel value="buy" control={<Radio />} label="삽니다" />
                    <FormControlLabel value="board" control={<Radio />} label="게시판" />
                </RadioGroup>
            </FormControl>
            <FormControl fullWidth>
            <TextField label="제목" className='txt_title' id="txt_title" onChange={onTitledHandleChange}/>
            </FormControl>

            <FormControl fullWidth >
                <InputLabel id="category_label">카테고리</InputLabel>
                <Select labelId="category_label" id="category_select" value={selectCategory} label="category" onChange={selectCategoryHandleChange}>
                    {renderItemCategory()}
                </Select>
            </FormControl>
            <FormControl fullWidth>
                <TextField label="희망가격" id="txt_price" onChange={onPriceHandleChange} InputProps={{endAdornment:(<InputAdornment position="end">원</InputAdornment>)}} />
                {/* <FormHelperText>희망가격</FormHelperText> */}
            </FormControl>
            <FormControl fullWidth>
            <TextField multiline label="설명" className='txt_description' id="txt_description" onChange={onDescriptionHandleChange} rows={10}/>
            </FormControl>
        </>
        );
    };
    
    const renderPostInfoFields = () => {
        return (
        <>
            <FormControl>
                <RadioGroup aria-labelledby="trade_name" name="trade" row defaultValue="sell" onChange={setClassHandleChange}>
                    <FormControlLabel value="sell" control={<Radio />} label="팝니다" />
                    <FormControlLabel value="buy" control={<Radio />} label="삽니다" />
                    <FormControlLabel value="board" control={<Radio />} label="게시판" />
                </RadioGroup>
            </FormControl>
            <FormControl fullWidth>
            <TextField  label="제목" className='txt_title' id="txt_title" onChange={onTitledHandleChange}/>
            </FormControl>

            <FormControl fullWidth >
                <InputLabel id="category_label">카테고리</InputLabel>
                <Select labelId="category_label" id="category_select" value={selectCategory} label="category" onChange={selectCategoryHandleChange}>
                    {renderPostCategory()}
                </Select>
            </FormControl>
            <FormControl fullWidth>
                <TextField multiline label="내용" className='txt_contents' id="txt_contents" onChange={onContentsHandleChange} rows={10}/>
            </FormControl>
        </>
        );
    };

    React.useEffect(() => {
        UseApi(getItemCategory, token, setToken, afterGetItemCategory);
        UseApi(getPostCategory, token, setToken, afterGetPostCategory);
    }, []);

    return (
        <>
        <HeaderContainer pageName={(classification==="sell"?"판매 물품 작성":(classification==="buy"?"구매 물품 작성":"게시물 작성"))} />
        <Container component="div" maxWidth="xs">
        <Box
                component="form"
                sx={{
                    '& > :not(style)': { mt: 2}
                }}
                noValidate
                autoComplete="off"
        >
            {classification==='board'?renderPostInfoFields():(classification==='sell'?renderItemInfoFields():renderBuyItemInfoFields())}
        </Box>
        {/* <Button onClick={() => save()}>저장</Button> */}
        <Button onClick={() => save()}>저장</Button>
        {/* afterItemSaved */}
        </Container>
        </>
    );
};

export default InputItemInfoPage;