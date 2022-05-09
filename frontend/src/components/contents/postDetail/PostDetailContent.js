import React from "react";
import { Paper } from "../../../../node_modules/@material-ui/core/index";

const PostDetailContent = (props) => {
    return (
        <Paper className="div_class">
            <div className="label">내용</div>
            <div className="content" id="post_con" style={{whiteSpace: "pre-wrap"}}>{props.content}</div>
        </Paper>
    );
};

export default PostDetailContent;