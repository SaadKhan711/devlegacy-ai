import { createRootRoute, Link, Outlet } from '@tanstack/react-router';
import { TanStackRouterDevtools } from '@tanstack/router-devtools';
import React from 'react';

export const Route = createRootRoute({
  component: () => (
    <>
      <div className="container flex max-w-screen-md flex-col gap-2">
        <div className="border-b p-2">
          <Link to="/" className="[&.active]:font-bold">
            Home
          </Link>{' '}
          <Link to="/about" className="[&.active]:font-bold">
            About
          </Link>{' '}
          <Link to="/app" className="[&.active]:font-bold">
            App
          </Link>{' '}
        </div>
        <Outlet />
      </div>
      <TanStackRouterDevtools />
    </>
  ),
});
