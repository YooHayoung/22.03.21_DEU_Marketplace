import React from "react";
import { Paper } from "../../../../node_modules/@material-ui/core/index";

const PostDetailTitle = (props) => {
    return (
        // <div className="div_class">
        <Paper className="div_class">
            <div className="label">제목</div>
            <div className="content">
                {`[${props.category.categoryName}] ${props.title}`}<br/>
                <span style={{fontSize: "0.7rem", color: "gray"}}>{props.createdDate} UPDATE</span>
            </div>
        </Paper>
        // </div>
    );
};

export default PostDetailTitle;