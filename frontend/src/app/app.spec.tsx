import { render, waitFor } from '@testing-library/react';
import React from 'react';

import  App  from './app';

test('renders all app html', async () => {
  const screen = render(<App />);
  await waitFor(() => {
    expect(screen.baseElement.outerHTML).toBeTruthy();
  });
});
