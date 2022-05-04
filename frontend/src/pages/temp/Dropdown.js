import {useState} from "react";
import "./Dropdown.css"
function Dropdown({selected, setSelected}){
    const [isActive, setIsActive]=useState(false)
    const option = ['전공','교양','일반']

    
    return(
        <div className="dropdown">
            <div className="dropdown-btn" onClick={(e) => 
                setIsActive(!isActive)}>
            {selected}
            <span className="fa down"></span>
            </div>
            {isActive && (
            <div className="dropdown-content">
                {option.map((option) => (
               <div 
               onClick={(e) => {
                setSelected(option);
                setIsActive(false);
                }}
                 className="dropdown-item"
                >
                 {option}
                </div>
                ))}
                </div>
            )}
        </div>
    )
}
export default Dropdown;