import React from "react";
import { Box, Button, Paper } from "../../../../node_modules/@material-ui/core/index";
import ThumbUpIcon from '@mui/icons-material/ThumbUp';

const PostDetailContent = (props) => {
    return (
        <>
        <Box className="div_class">
            <div className="label">내용</div>
            <div className="content" id="post_con" style={{whiteSpace: "pre-wrap"}}>{props.content}</div>
        </Box>
        {/* <Button startIcon={<ThumbUpIcon/>}>추천하기</Button> */}
        </>
    );
};

export default PostDetailContent;