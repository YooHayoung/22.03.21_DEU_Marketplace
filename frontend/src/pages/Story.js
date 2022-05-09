import * as React from 'react';
import './Story.scss';
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
import { FormHelperText, InputAdornment, OutlinedInput } from '../../node_modules/@material-ui/core/index';
import HeaderContainer from '../containers/HeaderContainer';
import { UseApi } from '../api/UseApi';
import { getItemCategory, getPostCategory } from '../api/Api';
import { Link } from '../../node_modules/react-router-dom/index';

const Story = ({token, setToken}) => {
    const [classification, setClassification] = React.useState('sell');
    const [selectCategory, setSelectCategory] = React.useState('');
    // const [selectPostCategory, setSelectPostCategory] = React.useState('');
    const [searchWord, setSearchWord] = React.useState('');
    const [priceLoe, setPriceLoe] = React.useState('');
    const [priceGoe, setPriceGoe]= React.useState('');

    const [itemCategory, setItemCategory] = React.useState([]);
    const [postCategory, setPostCategory] = React.useState([]);

    const [searchCond, setSearchCond] = React.useState({});

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

    React.useEffect(() => {
        UseApi(getItemCategory, token, setToken, afterGetItemCategory);
        UseApi(getPostCategory, token, setToken, afterGetPostCategory);
    }, []);

    const renderItemCategory = () => {
        if (itemCategory.length != 0) {
            return (itemCategory.map((category,idx)=> {
                return <MenuItem key={"mi"+idx} value={category}>{category.categoryName}</MenuItem>
            }));
        }
    };

    const renderPostCategory = () => {
        console.log(postCategory);
        if (postCategory.length != 0) {
            return (postCategory.map((category,idx)=> {
                return <MenuItem key={"mi"+idx} value={category}>{category.categoryName}</MenuItem>
            }));
        }
    };

    const onSearchWordHandleChange = (event) => {
        setSearchWord(event.target.value);
    };

    const onPriceLoeHandleChange = (event) => {
        setPriceLoe(event.target.value);
    };

    const onPriceGoeHandleChange = (event) => {
        setPriceGoe(event.target.value);
    };

    const renderItemSearch = () => {
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
            <TextField  label="검색어" className='searchWord' id="searchWord" onChange={onSearchWordHandleChange}/>
            </FormControl>

            <FormControl fullWidth >
                <InputLabel id="category_label">카테고리</InputLabel>
                <Select labelId="category_label" id="category_select" value={selectCategory} label="category" onChange={selectCategoryHandleChange}>
                    {/* <MenuItem value={10}>서적</MenuItem>
                    <MenuItem value={20}>전자기기</MenuItem>
                    <MenuItem value={30}>전체</MenuItem> */}
                    {renderItemCategory()}
                </Select>
            </FormControl>
            <Box className="div_price" fullWidth sx={{
                    '& > :not(style)': { mt: 0.6, mb: 1}
                }}>
                <FormControl className="priceGoe">
                    <TextField label="최소금액" id="priceGoe" onChange={onPriceGoeHandleChange} InputProps={{endAdornment:(<InputAdornment position="end">원</InputAdornment>)}} />
                </FormControl>
                <FormControl className="priceLoe">
                    <TextField label="최대금액" id="priceLoe" onChange={onPriceLoeHandleChange} InputProps={{endAdornment:(<InputAdornment position="end">원</InputAdornment>)}} />
                </FormControl> 
            </Box>
        </>
        );
    };
    
    const renderPostSearch = () => {
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
            <TextField  label="검색어" className='searchWord' id="searchWord" onChange={onSearchWordHandleChange} />
            </FormControl>

            <FormControl fullWidth >
                <InputLabel id="category_label">카테고리</InputLabel>
                <Select labelId="category_label" id="category_select" value={selectCategory} label="category" onChange={selectCategoryHandleChange}>
                    {renderPostCategory()}
                </Select>
            </FormControl>
        </>
        );
    };

    return (
        <>
        <HeaderContainer pageName={"검색"} />
        <Container component="div" maxWidth="xs">
            <Box
                component="form"
                sx={{
                    '& > :not(style)': { mt: 2}
                }}
                noValidate
                autoComplete="off"
            >
                {classification==='board'?renderPostSearch():renderItemSearch()}
            </Box>
            <Link to={`/${classification}`} 
                state= {{
                    category: selectCategory,
                    title: searchWord,
                    priceGoe: priceGoe,
                    priceLoe: priceLoe,
                }}>
                <Button type="submit" fullWidth variant='contained' sx={{mt:2}} onClick={()=>console.log(selectCategory)} >검색</Button>
            </Link>
        </Container>
        </>
    );
}

export default Story;