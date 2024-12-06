import {useEffect, useState} from 'react';
import SnowmanCard, {ISnowman} from './components/SnowmanCard';
import {Backdrop, Button, Fab, MenuItem, Paper, Select} from '@mui/material';
import {FETCH_CITY_ENDPOINT, SUBSCRIBE_CITY_ENDPOINT} from './endpoints';
import AddIcon from '@mui/icons-material/Add';
import PostOverlay from './components/PostOverlay';
import axios from 'axios';
import NotificationsActiveIcon from '@mui/icons-material/NotificationsActive';

function App() {
  const fetchCity = async () => {
    console.log('Fetching ' + city);
    let res = await axios.get(FETCH_CITY_ENDPOINT + '/' + city, {
      withCredentials: true,
    });
    let jsonSnowmen = res.data;
    console.log(jsonSnowmen);
    setSnowmen([...jsonSnowmen] as ISnowman[]);
  };
  const [city, setCity] = useState('Milwaukee');
  const [showOverlay, setShowOverlay] = useState(false);
  const [snowmen, setSnowmen] = useState<ISnowman[]>([]);
  useEffect(() => {
    fetchCity();
  }, [city]);

  return (
    <>
      <Select
        value={city}
        label="City"
        onChange={e => {
          setSnowmen([]);
          setCity(e.target.value);
        }}>
        <MenuItem value={'Milwaukee'}>Milwaukee</MenuItem>
        <MenuItem value={'Chicago'}>Chicago</MenuItem>
        <MenuItem value={'Dubai'}>Dubai</MenuItem>
      </Select>
      <Button
        onClick={() => {
          axios(SUBSCRIBE_CITY_ENDPOINT + '/' + city, {
            withCredentials: true,
            method: 'post',
          }).then(res => {
            console.log(typeof res.data);
            if (res.data) {
              alert('Successfully Subscribed');
            } else {
              alert('Successfully Unsubscribed');
            }
          });
        }}>
        <NotificationsActiveIcon />
      </Button>
      <Paper sx={{display: 'flex', flexWrap: 'wrap', flexDirection: 'row'}}>
        {snowmen.map(snowman => {
          return (
            <SnowmanCard
              refresh={() => {
                fetchCity();
              }}
              key={snowman.id}
              snowman={snowman}></SnowmanCard>
          );
        })}
      </Paper>
      <Fab
        sx={{position: 'fixed', bottom: 10, right: 10}}
        color="primary"
        onClick={() => {
          setShowOverlay(true);
        }}>
        <AddIcon />
      </Fab>
      <Backdrop
        sx={theme => ({color: '#fff', zIndex: theme.zIndex.drawer + 1})}
        open={showOverlay}>
        <PostOverlay
          city={city}
          refresh={() => {
            fetchCity();
          }}
          close={() => {
            setShowOverlay(false);
          }}
        />
      </Backdrop>
    </>
  );
}

export default App;
