import React from "react";
import { Button, Divider, FormControl, IconButton, InputBase, Paper, TextField } from "../../../../node_modules/@material-ui/core/index";
import AddCommentIcon from '@mui/icons-material/AddComment';
import InputUnstyled from '@mui/base/InputUnstyled';
import { Stack } from "../../../../node_modules/@mui/material/index";
import { styled } from '@mui/material/styles';

const Item = styled(Paper)(({ theme }) => ({
    backgroundColor: theme.palette.mode === 'dark' ? '#1A2027' : '#fff',
    ...theme.typography.body2,
    padding: theme.spacing(1),
    textAlign: 'center',
    color: theme.palette.text.secondary,
}));

const PostDetailCommentList = (props) => {
    const [comments, setComments] = React.useState([]);

    React.useEffect(() => {
        setComments([...comments, ...props.comments]);
    },[]);

    const renderComments = () => {
        return (comments.map((comment, idx) => {
            return (
                <>
                    <Stack
                        key={"st"+idx}
                        direction="row"
                        divider={<Divider orientation="vertical" flexItem />}
                        spacing={0}
                    >
                        <Item key={"nick" + idx}>{comment.memberInfo.nickname}</Item>
                        <Item key={"con" + idx}>ad1</Item>
                        <Item key={"time" + idx}>ad2</Item>
                    </Stack>
                </>
            )
        }))
    }

    return (
        <>
        <Paper className="div_paper">
            <div className="div_class">
                <div className="label">댓글</div><div className="content">{props.commentCount}개</div>
            </div>
            {renderComments()}
        </Paper>
        <FormControl fullWidth id="div_writeComment">
            <TextField multiline placeholder="댓글을 입력하세요" id="input_comment" variant="outlined" />
            <Button variant="contained" id="btn_sub">작성</Button>
        </FormControl>
        <Button onClick={() => console.log(comments)}>asd</Button>
        {/* </Paper> */}
        </>
    );
};

export default PostDetailCommentList;