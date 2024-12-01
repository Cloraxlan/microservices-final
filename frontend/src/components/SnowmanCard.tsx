import React from 'react';
import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import {Box} from '@mui/material';
import {REPORT_MISSING_ENDPOINT, REPORT_VISIT_ENDPOINT} from '../endpoints';
import axios from 'axios';

export interface ISnowman {
  id: number;
  username: string;
  imageURI: string;
  location: string;
  city: string;
  seenCount: number;
  missingCount: number;
}

interface Props {
  snowman: ISnowman;
  refresh: () => void;
}

function SnowmanCard(props: Props) {
  const reportVisit = () => {
    axios(REPORT_VISIT_ENDPOINT, {
      withCredentials: true,
      method: 'post',
      data: {id: props.snowman.id},
    }).then(() => {
      props.refresh();
    });
  };
  const reportMissing = () => {
    axios(REPORT_MISSING_ENDPOINT, {
      withCredentials: true,
      method: 'post',
      data: {id: props.snowman.id},
    }).then(() => {
      props.refresh();
    });
  };

  return (
    <Card sx={{maxWidth: 345, margin: 5}}>
      <CardMedia
        sx={{height: 300, objectFit: 'contain'}}
        image={props.snowman.imageURI}
      />
      <CardContent>
        <Typography gutterBottom variant="h5" component="div">
          Posted By : {props.snowman.username}
        </Typography>
        <Typography
          variant="body2"
          sx={{color: 'text.secondary', fontWeight: 'bold'}}>
          City : {props.snowman.city}
        </Typography>
        <Typography variant="body2" sx={{color: 'text.secondary'}}>
          Full Location : {props.snowman.location}
        </Typography>
        <Typography variant="body2" sx={{color: 'text.secondary'}}>
          Seen By {props.snowman.seenCount} people
        </Typography>
        <Typography variant="body2" sx={{color: 'text.secondary'}}>
          Reported Missing By {props.snowman.missingCount} people
        </Typography>
      </CardContent>
      <CardActions>
        <Button onClick={reportVisit} size="small">
          Report Visit
        </Button>
        <Button onClick={reportMissing} size="small">
          Report Missing
        </Button>
      </CardActions>
    </Card>
  );
}

export default SnowmanCard;
