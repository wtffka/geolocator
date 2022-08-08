import React, { useEffect, useState } from 'react';
import { useDispatch } from 'react-redux';
import { useTranslation } from 'react-i18next';
import { Link, useParams } from 'react-router-dom';
import {
  Card, Container, Row, Col, Button,
} from 'react-bootstrap';
import axios from 'axios';

import routes from '../../routes.js';

const Location = () => {
  const { t } = useTranslation();
  const params = useParams();
  const dispatch = useDispatch();

  const [location, setLocation] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const { data: locationData } = await axios.get(routes.location(params.locationId));
        setLocation(locationData);
      } catch (e) {
        setLocation(null);
      }
    };
    fetchData();
  }, [dispatch]);

  if (!location) {
    return null;
  }

  return (
    <Card>
      <Card.Header className="bg-dark text-white">
        <Card.Title>{location.coordinates}</Card.Title>
      </Card.Header>
      <Card.Body>
        <Container>
          <Row>
            <Col>
              {t('country')}
            </Col>
            <Col>
              <a href={`https://ru.wikipedia.org/wiki/${location.country}`}>{location.country}</a>
            </Col>
          </Row>
          <Row>
            <Col>
              {t('province')}
            </Col>
            <Col>
              <a href={`https://ru.wikipedia.org/wiki/${location.province}`}>{location.province}</a>
            </Col>
          </Row>
          <Row>
            <Col>
              {t('locality')}
            </Col>
            <Col>
              <a href={`https://ru.wikipedia.org/wiki/${location.locality}`}>{location.locality}</a>
            </Col>
          </Row>
          <Row>
            <Col>
              {t('street')}
            </Col>
            <Col>
              {location.street}
            </Col>
          </Row>
          <Row>
            <Col>
              {t('house')}
            </Col>
            <Col>
              {location.house}
            </Col>
          </Row>
          <Row>
            <Col>
              {t('otherDetails')}
            </Col>
            <Col>
              <a href={`https://ru.wikipedia.org/wiki/${location.otherDetails}`}>{location.otherDetails}</a>
            </Col>
          </Row>
          <Button variant="primary" className="btn-sm">
            <Link className="nav-link text-white" to={routes.locationsPagePath()}>
              {t('toLocationsPagePath')}
            </Link>
          </Button>
        </Container>
      </Card.Body>
    </Card>
  );
};

export default Location;
