import React from "react";
import { Box, Paper } from "../../../../node_modules/@material-ui/core/index";

const PostDetailContent = (props) => {
    return (
        <Box className="div_class">
            <div className="label">내용</div>
            <div className="content" id="post_con" style={{whiteSpace: "pre-wrap"}}>{props.content}</div>
        </Box>
    );
};

export default PostDetailContent;