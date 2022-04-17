import React, { useEffect } from 'react'; 
import { UncontrolledCarousel } from 'reactstrap'; 


const Carousel = (props) => {
    useEffect(() => {
        console.log(props);
    });

    
    return ( 
        <UncontrolledCarousel items={props.items}/> 
    );
 };

export default Carousel;

