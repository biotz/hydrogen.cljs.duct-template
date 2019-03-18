# hydrogen.cljs.duct-profile

An external profile for [Duct template](https://github.com/duct-framework/duct) that introduces Hydrogen boilerplate for cljs code.

## Usage

This template profile needs to be used in conjunction with `+site` and `+cljs` hints.

`lein new duct <project name> +site +cljs +hydrogen.cljs/core`

You can optionally add `+hydrogen.cljs/session` to add OIDC-based session management.

Once the project is generated go to `resources/<project-name>/config.edn` and change `:duct.router/cascading` key to
```
[#ig/ref :<project-name>.handler/root
 #ig/ref :<project-name>.handler/config]
```

(or to just `[#ig/ref :<project-name>.handler/root]` if you're not using session management)

## License

Copyright (c) Magnet S Coop 2018.

The source code for the library is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/.
