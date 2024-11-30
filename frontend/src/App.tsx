import {useEffect, useState} from 'react';
import SnowmanCard, {ISnowman} from './components/SnowmanCard';
import {Backdrop, Fab, MenuItem, Paper, Select} from '@mui/material';
import {FETCH_CITY_ENDPOINT} from './endpoints';
import {GoogleLogin} from '@react-oauth/google';
import AddIcon from '@mui/icons-material/Add';
import PostOverlay from './components/PostOverlay';

function App() {
  const test: ISnowman = {
    city: 'Milwaukee',
    imageURI: 'https://i.imgur.com/7bi99EE.jpeg',
    location: 'Dierks Hall',
    missingCount: 0,
    seenCount: 0,
    username: 'rozpadekk',
    id: 1,
  };
  const fetchCity = async () => {
    console.log('Fetching ' + city);
    let res = await fetch(FETCH_CITY_ENDPOINT + '/' + city, {
      method: 'GET',
      headers: {Authorization: `Bearer ${creds}`},
    });
    let jsonSnowmen = (await res.json()).snowmen;
    console.log(jsonSnowmen);
    setSnowmen([...jsonSnowmen] as ISnowman[]);
  };
  const [creds, setCreds] = useState('');
  const [city, setCity] = useState('Milwaukee');
  const [showOverlay, setShowOverlay] = useState(false);
  const [snowmen, setSnowmen] = useState<ISnowman[]>([]);
  useEffect(() => {
    fetchCity();
  }, [city]);

  return (
    <>
      {creds == '' && (
        <GoogleLogin
          onSuccess={credentialResponse => {
            console.log(credentialResponse);
            if (credentialResponse.credential) {
              setCreds(credentialResponse.credential);
            }
          }}
          onError={() => {
            console.log('Login Failed');
          }}
          useOneTap
        />
      )}

      {true && (
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
          <Paper sx={{display: 'flex', flexWrap: 'wrap', flexDirection: 'row'}}>
            {snowmen.map(snowman => {
              return (
                <SnowmanCard
                  refresh={() => {
                    fetchCity();
                  }}
                  creds={creds}
                  key={snowman.id}
                  snowman={snowman}></SnowmanCard>
              );
            })}
          </Paper>
          <Fab
            sx={{position: 'absolute', bottom: 10, right: 10}}
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
              creds={creds}
              refresh={() => {
                fetchCity();
              }}
              close={() => {
                setShowOverlay(false);
              }}
            />
          </Backdrop>
        </>
      )}
    </>
  );
}

export default App;
